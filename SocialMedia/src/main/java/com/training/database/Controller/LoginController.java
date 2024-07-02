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
    
    @Autowired
    private LoginService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        return userService.loginUser(user.getUserName(), user.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Profile profile) {
        return userService.registerUser(profile);
    }

    @PatchMapping("/update")
    public ResponseEntity<String> update(@RequestBody Profile profile) {
        return userService.updateUser(profile, profile.getEmail());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable("email") String email) {
        return userService.deleteUser(email);
    }
}
