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

    // Post Service class - Contains Business Logic for Posting content in the Application

    // A Custom Logger, other than the default logger
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LoginService userService;

    // // Constructor Injection
    // public PostService(UserService userService) {
    //     this.userService = userService;
    // }

    // Service to get all Posts
    public ResponseEntity<?> getAllPost() {
        try {
            // Retrieving all the posts in a List
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

    // Service to get all Posts for a given user name
    public ResponseEntity<?> getAllPostByUser(String userName) {
        try {
            // Retrieving all posts in a list
            List<Post> posts = postRepository.findAll();
            // Creating a new list to store the resulting posts filtered by user
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

    // Service to create a Post
    public ResponseEntity<?> createPost(String userName, String newPostContent) {
        try {
            // Creating a Post only when the user is logged
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            // Create a new Post
            Post newPost = new Post(userName, newPostContent);
            // Save the post in the repository
            postRepository.save(newPost);
            return new ResponseEntity<>("Post created succesfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating a post", e);
            return new ResponseEntity<>("An error occurred while creating a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Service to Edit a Post
    public ResponseEntity<?> editPost(int postId, String newPostContent) {
        try {
            // Editing a post only when the user is logged
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            // Finding the post by the given id
            Optional<Post> optionalPost = postRepository.findById(postId);
            if(optionalPost.isPresent()) {
                Post post = optionalPost.get();
                // Update the Post
                post.setPost(newPostContent);
                // Save the post in the repository
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

    // Service to Delete a Post
    public ResponseEntity<String> deletePost(int postId) {
        try {
            // Deleting a post only when the user is logged
            if(!userService.isUserLogged()) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            // Finding the post by the given id
            Optional<Post> optionalPost = postRepository.findById(postId);
            if(optionalPost.isPresent()) {
                // Delete the post from the repository
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
