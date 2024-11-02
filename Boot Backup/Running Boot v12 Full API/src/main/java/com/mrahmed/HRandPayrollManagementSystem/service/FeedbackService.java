package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Feedback;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    public Feedback updateFeedback(Long id, Feedback updatedFeedback) {
        Optional<Feedback> existingFeedback = feedbackRepository.findById(id);
        if (existingFeedback.isPresent()) {
            Feedback feedback = existingFeedback.get();
            feedback.setDate(updatedFeedback.getDate());
            feedback.setRating(updatedFeedback.getRating());
            feedback.setComment(updatedFeedback.getComment());
            feedback.setFeedbackMonth(updatedFeedback.getFeedbackMonth());
            feedback.setUser(updatedFeedback.getUser());
            return feedbackRepository.save(feedback);
        }
        return null;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getFeedbacksByEmail(String email) {
        return feedbackRepository.findFeedbacksByEmail(email);
    }

    public List<Feedback> getFeedbacksByName(String name) {
        return feedbackRepository.findFeedbacksByName(name);
    }

    public List<Feedback> getFeedbacksByUserId(Long userId) {
        return feedbackRepository.findFeedbacksByUserId(userId);
    }

    public List<Feedback> getFeedbacksByMonth(Month month) {
        return feedbackRepository.findFeedbacksByMonth(month);
    }

    public List<Feedback> getFeedbacksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return feedbackRepository.findFeedbacksByDateRange(startDate, endDate);
    }

    public List<Feedback> getLatestFeedbackByUserId(Long userId) {
        return feedbackRepository.findLatestFeedbackByUserId(userId);
    }

    public int countFeedbacksByUserId(Long userId) {
        return feedbackRepository.countFeedbacksByUserId(userId);
    }


}
