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

import com.training.database.Entity.Post;
import com.training.database.Service.PostService;

@RestController
@RequestMapping("/society")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> getPost() {
        return postService.getAllPost();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getPost(@PathVariable("userName") String userName) {
        return postService.getAllPostByUser(userName);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        return postService.createPost(post.getUserName(), post.getPost());
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        return postService.editPost(post.getId(), post.getPost());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
        return postService.deletePost(id);
    }
}
