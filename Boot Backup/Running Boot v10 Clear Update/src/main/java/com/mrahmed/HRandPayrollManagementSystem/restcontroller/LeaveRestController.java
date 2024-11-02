package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Leave;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.RequestStatus;
import com.mrahmed.HRandPayrollManagementSystem.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin("*")
public class LeaveRestController {

    @Autowired
    private LeaveService leaveService;


    @PostMapping("/create")
    public ResponseEntity<Leave> createLeaveRequest(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.saveLeaveRequest(leave);
        return ResponseEntity.ok(savedLeave);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Leave> updateLeaveRequest(@PathVariable Long id, @RequestBody Leave leave) {
        Leave updatedLeave = leaveService.updateLeaveRequest(id, leave);
        return ResponseEntity.ok(updatedLeave);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Long id) {
        Optional<Leave> leave = leaveService.getLeaveById(id);
        return leave.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Approve a leave request
    @PutMapping("/approve/{id}")
    public ResponseEntity<Leave> approveLeaveRequest(@PathVariable Long id) {
        Leave approvedLeave = leaveService.approveLeaveRequest(id);
        return ResponseEntity.ok(approvedLeave);
    }

    // Reject a leave request
    @PutMapping("/reject/{id}")
    public ResponseEntity<Leave> rejectLeaveRequest(@PathVariable Long id) {
        Leave rejectedLeave = leaveService.rejectLeaveRequest(id);
        return ResponseEntity.ok(rejectedLeave);
    }

    // Get all unpaid leaves by user ID
    @GetMapping("/user/{userId}/unpaid")
    public ResponseEntity<List<Leave>> getUnpaidLeavesByUserId(@PathVariable Long userId) {
        List<Leave> unpaidLeaves = leaveService.getUnpaidLeavesByUserId(userId);
        return ResponseEntity.ok(unpaidLeaves);
    }

    // Get leaves by request date
    @GetMapping("/request-date/{date}")
    public ResponseEntity<List<Leave>> getLeavesByRequestDate(@PathVariable LocalDate date) {
        List<Leave> leaves = leaveService.getLeavesByRequestDate(date);
        return ResponseEntity.ok(leaves);
    }

    // Get leaves by request status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Leave>> getLeavesByStatus(@PathVariable RequestStatus status) {
        List<Leave> leaves = leaveService.getLeavesByRequestStatus(status);
        return ResponseEntity.ok(leaves);
    }

    // Get leaves by year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Leave>> getLeavesByYear(@PathVariable int year) {
        List<Leave> leaves = leaveService.getLeavesByYear(year);
        return ResponseEntity.ok(leaves);
    }

    // Get unpaid leaves
    @GetMapping("/unpaid")
    public ResponseEntity<List<Leave>> getAllUnpaidLeaves() {
        List<Leave> unpaidLeaves = leaveService.getUnpaidLeaves();
        return ResponseEntity.ok(unpaidLeaves);
    }

    // Count leaves by user ID and status
    @GetMapping("/count/user/{userId}/status/{status}")
    public ResponseEntity<Long> countLeavesByUserAndStatus(@PathVariable Long userId, @PathVariable RequestStatus status) {
        long count = leaveService.countLeavesByUserAndStatus(userId, status);
        return ResponseEntity.ok(count);
    }

    // Get leaves by month
    @GetMapping("/month/{month}")
    public ResponseEntity<List<Leave>> getLeavesByMonth(@PathVariable Month month) {
        List<Leave> leaves = leaveService.getLeavesByMonth(month);
        return ResponseEntity.ok(leaves);
    }

    // Get pending leave requests
    @GetMapping("/pending")
    public ResponseEntity<List<Leave>> getPendingLeaveRequests() {
        List<Leave> pendingLeaves = leaveService.getPendingLeaveRequests();
        return ResponseEntity.ok(pendingLeaves);
    }

    // Get approved leave requests
    @GetMapping("/approved")
    public ResponseEntity<List<Leave>> getApprovedLeaveRequests() {
        List<Leave> approvedLeaves = leaveService.getApprovedLeaveRequests();
        return ResponseEntity.ok(approvedLeaves);
    }

    // Get rejected leave requests
    @GetMapping("/rejected")
    public ResponseEntity<List<Leave>> getRejectedLeaveRequests() {
        List<Leave> rejectedLeaves = leaveService.getRejectedLeaveRequests();
        return ResponseEntity.ok(rejectedLeaves);
    }

    // Get leaves by type
    @GetMapping("/type/{leaveType}")
    public ResponseEntity<List<Leave>> getLeavesByType(@PathVariable LeaveType leaveType) {
        List<Leave> leaves = leaveService.getLeavesByType(leaveType);
        return ResponseEntity.ok(leaves);
    }

    // Get approved leaves by user
    @GetMapping("/approved/user/{userId}")
    public ResponseEntity<List<Leave>> getApprovedLeavesByUser(@PathVariable Long userId) {
        List<Leave> leaves = leaveService.getApprovedLeavesByUser(userId);
        return ResponseEntity.ok(leaves);
    }

    // Get rejected leaves by user
    @GetMapping("/rejected/user/{userId}")
    public ResponseEntity<List<Leave>> getRejectedLeavesByUser(@PathVariable Long userId) {
        List<Leave> leaves = leaveService.getRejectedLeavesByUser(userId);
        return ResponseEntity.ok(leaves);
    }

    // Get leaves by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Leave>> getLeavesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Leave> leaves = leaveService.getLeavesByDateRange(startDate, endDate);
        return ResponseEntity.ok(leaves);
    }

    // Get leaves by reason
    @GetMapping("/reason")
    public ResponseEntity<List<Leave>> getLeavesByReason(@RequestParam String reason) {
        List<Leave> leaves = leaveService.getLeavesByReason(reason);
        return ResponseEntity.ok(leaves);
    }

    // Get leaves by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Leave>> getLeavesByUserId(@PathVariable Long userId) {
        List<Leave> leaves = leaveService.getLeavesByUserId(userId);
        return ResponseEntity.ok(leaves);
    }

    // Calculate remaining leaves by user
    @GetMapping("/remaining/user/{userId}")
    public ResponseEntity<Integer> calculateRemainingLeavesByUser(@PathVariable Long userId) {
        Integer remainingLeaves = leaveService.calculateRemainingLeavesByUser(userId);
        return ResponseEntity.ok(remainingLeaves);
    }

    // Get total unpaid leave days for a specific user
    @GetMapping("/unpaid/user/{userId}")
    public ResponseEntity<Integer> getTotalUnpaidLeaveDays(@PathVariable Long userId, @RequestParam List<LeaveType> leaveTypes) {
        int totalUnpaidDays = leaveService.getTotalUnpaidLeaveDays(userId, leaveTypes);
        return ResponseEntity.ok(totalUnpaidDays);
    }
}

