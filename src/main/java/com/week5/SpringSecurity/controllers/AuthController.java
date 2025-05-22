package com.week5.SpringSecurity.controllers;

import com.week5.SpringSecurity.dto.LoginDTO;
import com.week5.SpringSecurity.dto.SingUpDTO;
import com.week5.SpringSecurity.dto.UserDTO;
import com.week5.SpringSecurity.services.AuthService;
import com.week5.SpringSecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SingUpDTO singUpDTO) {
        UserDTO user = userService.signUp(singUpDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> logIn(@RequestBody LoginDTO loginDTO) {
         String token = authService.logIn(loginDTO);
        return ResponseEntity.ok(token);
    }
}
