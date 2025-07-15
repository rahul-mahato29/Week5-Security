package com.week5.SpringSecurity.services;

import com.week5.SpringSecurity.dto.LoginDTO;
import com.week5.SpringSecurity.dto.SingUpDTO;
import com.week5.SpringSecurity.dto.UserDTO;
import com.week5.SpringSecurity.entities.UserEntity;
import com.week5.SpringSecurity.exceptions.ResourceNotFoundException;
import com.week5.SpringSecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User - " + username + " not found"));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public UserDTO signUp(SingUpDTO singUpDTO) {
        Optional<UserEntity> user = userRepository.findByEmail(singUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with this email already exist : "+singUpDTO.getEmail());
        }

        UserEntity toBeCreatedUser = modelMapper.map(singUpDTO, UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        UserEntity savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
