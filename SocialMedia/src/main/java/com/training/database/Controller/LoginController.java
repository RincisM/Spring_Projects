package com.training.database.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.database.Entity.Profile;
import com.training.database.Entity.Users;
import com.training.database.Service.LoginService;

@RestController
@RequestMapping("/society")
public class LoginController {

    // Controller class to handle the http requests for login service of social media application
    
    @Autowired
    private LoginService loginService;

    // Method to Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        return loginService.loginUser(user);
    }

    // Method to Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Profile profile) {
        return loginService.registerUser(profile);
    }

    // Method to Update the User Profile
    @PatchMapping("/update")
    public ResponseEntity<String> update(@RequestBody Profile profile) {
        return loginService.updateUser(profile);
    }

    // Method to delete the User Profile
    @DeleteMapping("/user/{email}")
    public ResponseEntity<String> delete(@PathVariable("email") String email) {
        return loginService.deleteUser(email);
    }
}
