package com.training.database.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.database.DTO.CreatePostRequest;
import com.training.database.Entity.Post;
import com.training.database.Service.PostService;

@RestController
@RequestMapping("/society")
public class PostController {

    // Controller class to handle the http request to Post content to the Social Media Application

    @Autowired
    private PostService postService;

    // Method to Get all the posts
    @GetMapping
    public ResponseEntity<?> getPost() {
        return postService.getAllPost();
    }

    // Method to get posts by username
    @GetMapping("/{userName}")
    public ResponseEntity<?> getPost(@PathVariable("userName") String userName) {
        return postService.getAllPostByUser(userName);
    }

    // Method to create new post
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest.getUserName(), createPostRequest.getPostContent());
    }

    // Method to update a post by a given id
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") int id, @RequestBody String post) {
        return postService.editPost(id, post);
    }

    // Method to delete a post by a given id
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
        return postService.deletePost(id);
    }
}
