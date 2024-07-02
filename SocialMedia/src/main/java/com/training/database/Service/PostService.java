package com.training.database.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.training.database.Entity.Post;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    public List<Post> posts = new ArrayList<>();

    @Autowired
    private LoginService userService;

    // // Constructor Injection
    // public PostService(UserService userService) {
    //     this.userService = userService;
    // }

    // To get all Posts
    public ResponseEntity<?> getAllPost() {
        try {
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
            posts.add(newPost);
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
            boolean postUpdated = false;
            for(Post post: posts) {
                if(post.getId() == postId) {
                    post.setPost(newPostContent);
                    postUpdated = true;
                    break;
                }
            }
            if(postUpdated) {
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
            boolean deleted = false;
            for(Post post: posts) {
                if(post.getId() == postId) {
                    posts.remove(post);
                    deleted = true;
                    break;
                }
            }
            if(deleted) {
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
