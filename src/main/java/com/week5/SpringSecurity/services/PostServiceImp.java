package com.week5.SpringSecurity.services;

import com.week5.SpringSecurity.dto.PostDTO;
import com.week5.SpringSecurity.entities.PostEntity;
import com.week5.SpringSecurity.exceptions.ResourceNotFoundException;
import com.week5.SpringSecurity.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post Not Found with id : "+id));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(Long id, Map<String, Object> updatedVal) {
        Optional<PostEntity> optionalEntity = postRepository.findById(id);
        if (optionalEntity.isEmpty()) return null;

        PostEntity entity = optionalEntity.get();

        updatedVal.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(PostEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, entity, value);
        });

        return modelMapper.map(postRepository.save(entity), PostDTO.class);
    }
}
