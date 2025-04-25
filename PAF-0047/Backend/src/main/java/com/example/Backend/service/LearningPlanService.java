package com.example.Backend.service;

import com.example.Backend.model.LearningPlan;
import com.example.Backend.model.LearningPlan.Course;
import com.example.Backend.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    // Create a new learning plan
    public LearningPlan createLearningPlan(LearningPlan plan) {
        if (plan.getUserId() == null || plan.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (plan.getUserName() == null || plan.getUserName().isEmpty()) {
            plan.setUserName("Unknown User");
        }
        plan.setCreatedAt(new Date());
        plan.setUpdatedAt(new Date());
        plan.setEnrolledCourses(new ArrayList<>());
        plan.setProgress(0.0);
        plan.setBadges(new ArrayList<>());
        return learningPlanRepository.save(plan);
    }

    // Get all learning plans
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAllByOrderByCreatedAtDesc();
    }

    // Get a learning plan by ID
    public LearningPlan getLearningPlanById(String id) {
        return learningPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Learning plan not found"));
    }

    // Get plans by user ID
    public List<LearningPlan> getLearningPlansByUserId(String userId) {
        return learningPlanRepository.findByUserId(userId);
    }

    // Update learning plan
    public LearningPlan updateLearningPlan(String id, LearningPlan planDetails) {
        LearningPlan plan = getLearningPlanById(id);
        plan.setTitle(planDetails.getTitle());
        plan.setDescription(planDetails.getDescription());
        plan.setTopics(planDetails.getTopics());
        plan.setResources(planDetails.getResources());
        plan.setUpdatedAt(new Date());
        return learningPlanRepository.save(plan);
    }

    // Delete learning plan
    public void deleteLearningPlan(String id) {
        LearningPlan plan = getLearningPlanById(id);
        learningPlanRepository.delete(plan);
    }

    // Enroll in a course
    public LearningPlan enrollCourse(String planId, Course course) {
        LearningPlan plan = getLearningPlanById(planId);
        if (plan.getEnrolledCourses() == null) {
            plan.setEnrolledCourses(new ArrayList<>());
        }
        boolean alreadyEnrolled = plan.getEnrolledCourses().stream()
                .anyMatch(c -> c.getCourseId().equals(course.getCourseId()));
        if (!alreadyEnrolled) {
            course.setEnrolledAt(new Date());
            course.setCompleted(false);
            plan.getEnrolledCourses().add(course);
            updateProgressAndBadges(plan);
            return learningPlanRepository.save(plan);
        }
        return plan;
    }

    // Remove a course
    public LearningPlan removeCourse(String planId, String courseId) {
        LearningPlan plan = getLearningPlanById(planId);
        plan.setEnrolledCourses(plan.getEnrolledCourses().stream()
                .filter(course -> !course.getCourseId().equals(courseId))
                .collect(Collectors.toList()));
        updateProgressAndBadges(plan);
        return learningPlanRepository.save(plan);
    }

    // Mark course as completed
    public LearningPlan markCourseCompleted(String planId, String courseId) {
        LearningPlan plan = getLearningPlanById(planId);
        plan.getEnrolledCourses().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst()
                .ifPresent(course -> course.setCompleted(true));
        updateProgressAndBadges(plan);
        return learningPlanRepository.save(plan);
    }

    // Update progress and assign badges
    private void updateProgressAndBadges(LearningPlan plan) {
        if (plan.getEnrolledCourses() == null || plan.getEnrolledCourses().isEmpty()) {
            plan.setProgress(0.0);
            plan.setBadges(new ArrayList<>());
            return;
        }

        // Calculate progress
        long totalCourses = plan.getEnrolledCourses().size();
        long completedCourses = plan.getEnrolledCourses().stream()
                .filter(Course::isCompleted)
                .count();
        double progress = (completedCourses * 100.0) / totalCourses;
        plan.setProgress(progress);

        // Assign badges based on progress
        List<String> badges = new ArrayList<>();
        if (progress >= 25)
            badges.add("Beginner");
        if (progress >= 50)
            badges.add("Intermediate");
        if (progress >= 75)
            badges.add("Advanced");
        if (progress == 100)
            badges.add("Master");
        plan.setBadges(badges);
    }
}