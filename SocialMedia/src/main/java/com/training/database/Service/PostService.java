package com.training.database.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.training.database.Entity.Post;
import com.training.database.Repository.PostRepository;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LoginService userService;

    // // Constructor Injection
    // public PostService(UserService userService) {
    //     this.userService = userService;
    // }

    // To get all Posts
    public ResponseEntity<?> getAllPost() {
        try {
            List<Post> posts = postRepository.findAll();
            if(!posts.isEmpty()) {
                return new ResponseEntity<>(posts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Posts found. Create new Post", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.error("Error retrieving posts", e);
            return new ResponseEntity<>("An error occurred while retrieving posts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To get all Posts for a given user name
    public ResponseEntity<?> getAllPostByUser(String userName) {
        try {
            List<Post> posts = postRepository.findAll();
            List<Post> postByUser = new ArrayList<>();
            for(Post post: posts) {
                if(post.getUserName().equals(userName)) {
                    postByUser.add(post);
                }
            }
            if(!postByUser.isEmpty()) {
                return new ResponseEntity<>(postByUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Posts found. Create new Post", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.error("Error retrieving posts", e);
            return new ResponseEntity<>("An error occurred while retrieving posts by userName", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To create a Post
    public ResponseEntity<?> createPost(String userName, String newPostContent) {
        try {
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            Post newPost = new Post(userName, newPostContent);
            postRepository.save(newPost);
            return new ResponseEntity<>("Post created succesfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating a post", e);
            return new ResponseEntity<>("An error occurred while creating a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To Edit a Post
    public ResponseEntity<?> editPost(int postId, String newPostContent) {
        try {
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            Optional<Post> optionalPost = postRepository.findById(postId);
            if(optionalPost.isPresent()) {
                Post post = optionalPost.get();
                post.setPost(newPostContent);
                postRepository.save(post);
                return new ResponseEntity<>("Post Updated Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error editing a post", e);
            return new ResponseEntity<>("An error occurred while editing the post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To Delete a Post
    public ResponseEntity<String> deletePost(int postId) {
        try {
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            Optional<Post> optionalPost = postRepository.findById(postId);
            if(optionalPost.isPresent()) {
                postRepository.delete(optionalPost.get());
                return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Post with ID: "+ postId + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting a post", e);
            return new ResponseEntity<>("An error occurred while deleting a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
