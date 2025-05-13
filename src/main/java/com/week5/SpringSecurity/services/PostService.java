package com.week5.SpringSecurity.services;

import com.week5.SpringSecurity.dto.PostDTO;

import java.util.List;
import java.util.Map;

public interface PostService {
    PostDTO createNewPost(PostDTO inputPost);

    List<PostDTO> getAllPost();

    PostDTO getPostById(Long id);

    PostDTO updatePostById(Long id, Map<String, Object> updatedVal);
}
