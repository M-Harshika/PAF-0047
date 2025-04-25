package com.example.Backend.controller;

import com.example.Backend.model.User;
import com.example.Backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = profileService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String email) {
        List<User> users = profileService.searchUsersByEmail(email);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/follow/{followerId}/{followeeId}")
    public ResponseEntity<String> followUser(@PathVariable String followerId, @PathVariable String followeeId) {
        String result = profileService.followUser(followerId, followeeId);
        if (result.startsWith("Successfully")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(400).body(result);
    }

    @PostMapping("/unfollow/{followerId}/{followeeId}")
    public ResponseEntity<String> unfollowUser(@PathVariable String followerId, @PathVariable String followeeId) {
        String result = profileService.unfollowUser(followerId, followeeId);
        if (result.startsWith("Successfully")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(400).body(result);
    }
}