package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Feedback;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin("*")
public class FeedbackRestController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback updatedFeedback) {
        Feedback feedback = feedbackService.updateFeedback(id, updatedFeedback);
        if (feedback != null) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        if (feedback.isPresent()) {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Feedback>> getFeedbacksByEmail(@PathVariable String email) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByEmail(email);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Feedback>> getFeedbacksByName(@PathVariable String name) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByName(name);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable Long userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<Feedback>> getFeedbacksByMonth(@PathVariable Month month) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByMonth(month);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/daterange")
    public ResponseEntity<List<Feedback>> getFeedbacksByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByDateRange(startDate, endDate);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/latest/{userId}")
    public ResponseEntity<List<Feedback>> getLatestFeedbackByUserId(@PathVariable Long userId) {
        List<Feedback> feedbacks = feedbackService.getLatestFeedbackByUserId(userId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> countFeedbacksByUserId(@PathVariable Long userId) {
        int count = feedbackService.countFeedbacksByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


}
