package com.example.Backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "learning_plan")
public class LearningPlan {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String title;
    private String description;
    private String topics;
    private String resources;
    private Date createdAt;
    private Date updatedAt;
    private List<Course> enrolledCourses;
    private double progress;
    private List<String> badges;

    // Nested Course class
    public static class Course {
        private String courseId;
        private String courseName;
        private boolean completed;
        private Date enrolledAt;

        public Course() {
        }

        public Course(String courseId, String courseName, boolean completed, Date enrolledAt) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.completed = completed;
            this.enrolledAt = enrolledAt;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public Date getEnrolledAt() {
            return enrolledAt;
        }

        public void setEnrolledAt(Date enrolledAt) {
            this.enrolledAt = enrolledAt;
        }
    }

    // Constructors
    public LearningPlan() {
        this.enrolledCourses = new ArrayList<>();
        this.progress = 0.0;
        this.badges = new ArrayList<>();
    }

    public LearningPlan(String id, String userId, String userName, String title, String description, String topics,
            String resources, Date createdAt, Date updatedAt, List<Course> enrolledCourses, double progress,
            List<String> badges) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.topics = topics;
        this.resources = resources;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.progress = progress;
        this.badges = badges != null ? badges : new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }
}