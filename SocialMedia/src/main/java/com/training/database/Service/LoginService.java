package com.training.database.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.training.database.Entity.Profile;
import com.training.database.Entity.Users;
import com.training.database.Repository.ProfileRepository;
import com.training.database.Repository.UserRepository;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private boolean userLogged = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    // Service for an User to Login
    public ResponseEntity<String> loginUser(String userName, String password) {
        ResponseEntity<String> response;
        try {
            Optional<Users> optionalUser = userRepository.findByUserName(userName);
            String message = "No Such User Exists";
            if(optionalUser.isPresent()) {
                Users user = optionalUser.get();
                if(user.getPassword().equals(password)) {
                    userLogged = true;
                    message = "User Logged in Successfully";
                } else {
                    message = "Invalid Password";
                }
            }
            response = ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("An error occurred during login of an User", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    // Service for new user to Register
    public ResponseEntity<String> registerUser(Profile newUserProfile) {
        ResponseEntity<String> response;
        try {
            Optional<Profile> existingProfile = profileRepository.findByUserName(newUserProfile.getUserName());
            logger.info("repository created");
            String message = "No Such User Exists";
            if(existingProfile.isPresent()) {
                logger.info("Checking username");
                message = "Username Already Exists";
            } else {
                logger.info("new user exists");
                Users newUser = new Users(newUserProfile.getUserName(), newUserProfile.getPassword());
                userRepository.save(newUser);
                profileRepository.save(newUserProfile);
                message = "User created Successfully";
                userLogged = true;
            }
            response = ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("An error occurred during registration of an User", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    public ResponseEntity<String> updateUser(Profile newUserProfile, String email) {
        ResponseEntity<String> response;
        boolean userUpdated = false;
        try {
            Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
            String message = "No Such User Exists";
            if(optionalProfile.isPresent()) {
                Profile existingProfile = optionalProfile.get();
                existingProfile.setFirstName(newUserProfile.getFirstName());
                existingProfile.setLastName(newUserProfile.getLastName());
                existingProfile.setAge(newUserProfile.getAge());
                existingProfile.setUserName(newUserProfile.getUserName());
                existingProfile.setPassword(newUserProfile.getPassword());
                profileRepository.save(existingProfile);
                userUpdated = true;
            }
            if(userUpdated) {
               message = "User updated Successfully";
            }
            response = ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("An error occurred during updating the profile of an User", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<String> deleteUser(String email) {
        ResponseEntity<String> response;
        boolean userDeleted = false;
        try {
            Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
            String message = "No Such User Exists";
            if(optionalProfile.isPresent()) {
                profileRepository.delete(optionalProfile.get());
                userDeleted = true;
            }

            if(userDeleted) {
               message = "User deleted Successfully";
            }
            response = ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("An error occurred during deletion of an User", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public boolean isUserLogged() {
        return userLogged;
    }

    public void logoutUser() {
        userLogged = false;
    }

}
