package com.week5.SpringSecurity.controllers;

import com.week5.SpringSecurity.dto.PostDTO;
import com.week5.SpringSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable(name = "postId") Long id){
        return postService.getPostById(id);
    }

    @PutMapping(path = "/update/{postId}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable(name = "postId") Long id, @RequestBody Map<String, Object> updatedVal){
        return ResponseEntity.ok(postService.updatePostById(id, updatedVal));
    }
}
