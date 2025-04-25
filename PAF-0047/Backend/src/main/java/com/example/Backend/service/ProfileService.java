package com.example.Backend.service;

import com.example.Backend.model.User;
import com.example.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getProfile(String userId) {
        return userRepository.findById(userId);
    }

    public String followUser(String followerId, String followeeId) {
        if (followerId.equals(followeeId)) {
            return "Cannot follow yourself";
        }

        Optional<User> followerOpt = userRepository.findById(followerId);
        Optional<User> followeeOpt = userRepository.findById(followeeId);

        if (!followerOpt.isPresent() || !followeeOpt.isPresent()) {
            return "User not found";
        }

        User follower = followerOpt.get();
        if (follower.getFollowing().contains(followeeId)) {
            return "Already following this user";
        }

        follower.getFollowing().add(followeeId);
        userRepository.save(follower);
        return "Successfully followed user";
    }

    public String unfollowUser(String followerId, String followeeId) {
        Optional<User> followerOpt = userRepository.findById(followerId);
        if (!followerOpt.isPresent()) {
            return "User not found";
        }

        User follower = followerOpt.get();
        if (!follower.getFollowing().contains(followeeId)) {
            return "Not following this user";
        }

        follower.getFollowing().remove(followeeId);
        userRepository.save(follower);
        return "Successfully unfollowed user";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }
}