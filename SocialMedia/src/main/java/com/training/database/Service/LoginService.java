package com.training.database.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.training.database.Entity.Profile;
import com.training.database.Entity.Users;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private boolean userLogged = false;
    private List<Users> users = new ArrayList<>();
    private List<Profile> userProfiles = new ArrayList<>();

    // Service for an User to Login
    public ResponseEntity<String> loginUser(String userName, String password) {
        ResponseEntity<String> response;
        try {
            String message = "No Such User Exists";
            for(Users user: users) {
                if(user.getUserName().equals(userName)) {
                    if(user.getPassword().equals(password)) {
                        userLogged = true;
                        message = "User Logged in Successfully";
                        break;
                    } else {
                        message = "Invalid Password";
                    }
                    break;
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
        boolean newUser = true;
        try {
            String message = "No Such User Exists";
            for(Profile userProfile: userProfiles) {
                if(userProfile.getUserName().equals(newUserProfile.getUserName())) {
                    newUser = false;
                    message = "Username Already Exists";
                    break;
                }
            }
            if(newUser) {
               userProfiles.add(newUserProfile);
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
            String message = "No Such User Exists";
            for(int i = 0; i < userProfiles.size(); i++) {
                if(userProfiles.get(i).getEmail().equals(email)) {
                    userProfiles.get(i).setFirstName(newUserProfile.getFirstName());
                    userProfiles.get(i).setLastName(newUserProfile.getLastName());
                    userProfiles.get(i).setAge(newUserProfile.getAge());
                    userProfiles.get(i).setUserName(newUserProfile.getUserName());
                    userProfiles.get(i).setPassword(newUserProfile.getPassword());
                    userUpdated = true;
                    break;
                }
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
            String message = "No Such User Exists";
            for(Profile userProfile: userProfiles) {
                if(userProfile.getEmail().equals(email)) {
                    userDeleted = true;
                    userProfiles.remove(userProfile);
                    break;
                }
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
