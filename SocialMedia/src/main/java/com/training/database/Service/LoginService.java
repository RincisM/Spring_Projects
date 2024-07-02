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

    // Login Service class - Contains Business Logic for Login to the Application

    // A Custom Logger, other than the default logger
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
            // Finding if a particular userName exists already
            Optional<Users> optionalUser = userRepository.findByUserName(userName);
            String message = "No Such User Exists";
            if(optionalUser.isPresent()) {
                Users user = optionalUser.get();
                // If UserName exists, finding if the password match
                if(user.getPassword().equals(password)) {
                    // If password matched, user is logged in
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
            // Finding if a username of the given profile is present elsewhere.
            Optional<Profile> existingProfile = profileRepository.findByUserName(newUserProfile.getUserName());
            logger.info("repository created");
            String message = "No Such User Exists";
            if(existingProfile.isPresent()) {
                logger.info("Checking username");
                message = "Username Already Exists";
            } else {
                // If the username is not present in the database, create a new user
                logger.info("new user exists");
                Users newUser = new Users(newUserProfile.getUserName(), newUserProfile.getPassword());
                // Store the username and password in users repository
                userRepository.save(newUser);
                // Store the profile in profile repository
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

    // Service to update the user profile
    public ResponseEntity<String> updateUser(Profile newUserProfile, String email) {
        ResponseEntity<String> response;
        boolean userUpdated = false;
        try {
            // Finding the Profile of a user by their email
            Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
            String message = "No Such User Exists";
            if(optionalProfile.isPresent()) {
                // If a profile is found, update it fields
                Profile existingProfile = optionalProfile.get();
                existingProfile.setFirstName(newUserProfile.getFirstName());
                existingProfile.setLastName(newUserProfile.getLastName());
                existingProfile.setAge(newUserProfile.getAge());
                existingProfile.setUserName(newUserProfile.getUserName());
                existingProfile.setPassword(newUserProfile.getPassword());
                // Store the updated profile in the repository
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

    // Service to delete an user profile
    public ResponseEntity<String> deleteUser(String email) {
        ResponseEntity<String> response;
        boolean userDeleted = false;
        try {
            // Finding if the profile with an email exists
            Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
            String message = "No Such User Exists";
            if(optionalProfile.isPresent()) {
                Profile profile = optionalProfile.get();

                // Delete the corresponding user from the users_table
                Optional<Users> optionalUser = userRepository.findByUserName(profile.getUserName());
                if(optionalUser.isPresent()) {
                    userRepository.delete(optionalUser.get());
                }

                // Delete the profile from the profile_table
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
