package com.example.Backend.controller;

import com.example.Backend.model.LearningPlan;
import com.example.Backend.model.LearningPlan.Course;
import com.example.Backend.service.LearningPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-plan")
@CrossOrigin(origins = "*")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

    // Create a learning plan for a specific user
    @PostMapping("/user/{userId}")
    public ResponseEntity<LearningPlan> createLearningPlanForUser(
            @PathVariable String userId,
            @RequestBody LearningPlan learningPlan) {
        learningPlan.setUserId(userId);
        LearningPlan created = learningPlanService.createLearningPlan(learningPlan);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all learning plans
    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> plans = learningPlanService.getAllLearningPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    // Get a learning plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<LearningPlan> getLearningPlanById(@PathVariable String id) {
        LearningPlan plan = learningPlanService.getLearningPlanById(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    // Get learning plans by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByUserId(@PathVariable String userId) {
        List<LearningPlan> plans = learningPlanService.getLearningPlansByUserId(userId);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    // Update a learning plan
    @PutMapping("/{id}")
    public ResponseEntity<LearningPlan> updateLearningPlan(
            @PathVariable String id,
            @RequestBody LearningPlan learningPlan) {
        LearningPlan updated = learningPlanService.updateLearningPlan(id, learningPlan);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete a learning plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearningPlan(@PathVariable String id) {
        learningPlanService.deleteLearningPlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Enroll in a course
    @PostMapping("/{planId}/courses")
    public ResponseEntity<LearningPlan> enrollCourse(
            @PathVariable String planId,
            @RequestBody Course course) {
        LearningPlan updated = learningPlanService.enrollCourse(planId, course);
        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }

    // Remove a course
    @DeleteMapping("/{planId}/courses/{courseId}")
    public ResponseEntity<LearningPlan> removeCourse(
            @PathVariable String planId,
            @PathVariable String courseId) {
        LearningPlan updated = learningPlanService.removeCourse(planId, courseId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Mark course as completed
    @PutMapping("/{planId}/courses/{courseId}/complete")
    public ResponseEntity<LearningPlan> markCourseCompleted(
            @PathVariable String planId,
            @PathVariable String courseId) {
        LearningPlan updated = learningPlanService.markCourseCompleted(planId, courseId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}