package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Leave;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.RequestStatus;
import com.mrahmed.HRandPayrollManagementSystem.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    public Leave saveLeaveRequest(Leave leave) {
        int remainingDays = calculateRemainingLeaveDays(leave.getStartDate(), leave.getEndDate());
        leave.setRemainingLeave(remainingDays);
        return leaveRepository.save(leave);
    }

    public Leave updateLeaveRequest(Long id, Leave updatedLeave) {
        Optional<Leave> existingLeaveOpt = leaveRepository.findById(id);
        if (existingLeaveOpt.isPresent()) {
            Leave existingLeave = existingLeaveOpt.get();
            existingLeave.setStartDate(updatedLeave.getStartDate());
            existingLeave.setEndDate(updatedLeave.getEndDate());
            existingLeave.setRequestDate(updatedLeave.getRequestDate());
            existingLeave.setReason(updatedLeave.getReason());
            existingLeave.setRemainingLeave(updatedLeave.getRemainingLeave());
            existingLeave.setYear(updatedLeave.getYear());
            existingLeave.setLeaveMonth(updatedLeave.getLeaveMonth());
            existingLeave.setLeaveType(updatedLeave.getLeaveType());
            existingLeave.setRequestStatus(updatedLeave.getRequestStatus());
            existingLeave.setUser(updatedLeave.getUser());
            return leaveRepository.save(existingLeave);
        }
        throw new RuntimeException("Leave request not found with ID: " + id);
    }

    public void deleteLeave(Long leaveId) {
        leaveRepository.deleteById(leaveId);
    }

    public Optional<Leave> getLeaveById(Long leaveId) {
        return leaveRepository.findById(leaveId);
    }

    public Leave approveLeaveRequest(Long leaveId) {
        Optional<Leave> leaveOpt = leaveRepository.findById(leaveId);
        if (leaveOpt.isPresent()) {
            Leave leave = leaveOpt.get();
            leave.setRequestStatus(RequestStatus.APPROVED);
            return leaveRepository.save(leave);
        }
        throw new RuntimeException("Leave request not found with ID: " + leaveId);
    }

    public Leave rejectLeaveRequest(Long leaveId) {
        Optional<Leave> leaveOpt = leaveRepository.findById(leaveId);
        if (leaveOpt.isPresent()) {
            Leave leave = leaveOpt.get();
            leave.setRequestStatus(RequestStatus.REJECTED);
            return leaveRepository.save(leave);
        }
        throw new RuntimeException("Leave request not found with ID: " + leaveId);
    }

    public List<Leave> getUnpaidLeavesByUserId(Long userId) {
        return leaveRepository.findUnpaidLeavesByUserId(userId);
    }

    public List<Leave> getLeavesByRequestDate(LocalDate date) {
        return leaveRepository.findLeaveByRequestDate(date);
    }

    public List<Leave> getLeavesByRequestStatus(RequestStatus status) {
        return leaveRepository.findLeaveByRequestStatus(status);
    }

    public List<Leave> getLeavesByYear(int year) {
        return leaveRepository.findAllLeaveByYear(year);
    }

    public List<Leave> getUnpaidLeaves() {
        return leaveRepository.findUnpaidLeaves();
    }

    public long countLeavesByUserAndStatus(Long userId, RequestStatus status) {
        return leaveRepository.countLeavesByUserAndStatus(userId, status);
    }

    public List<Leave> getLeavesByMonth(Month month) {
        return leaveRepository.findLeavesByMonth(month);
    }

    public List<Leave> getPendingLeaveRequests() {
        return leaveRepository.findPendingLeaveRequests();
    }

    public List<Leave> getApprovedLeaveRequests() {
        return leaveRepository.findApprovedLeaveRequests();
    }

    public List<Leave> getRejectedLeaveRequests() {
        return leaveRepository.findRejectedLeaveRequests();
    }

    public List<Leave> getLeavesByType(LeaveType leaveType) {
        return leaveRepository.findLeavesByType(leaveType);
    }

    public List<Leave> getApprovedLeavesByUser(Long userId) {
        return leaveRepository.findApprovedLeavesByUser(userId);
    }

    public List<Leave> getRejectedLeavesByUser(Long userId) {
        return leaveRepository.findRejectedLeavesByUser(userId);
    }

    public List<Leave> getLeavesByDateRange(LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findLeavesByDateRange(startDate, endDate);
    }


    public Integer calculateRemainingLeavesByUser(Long userId) {
        return leaveRepository.calculateRemainingLeavesByUser(userId);
    }

    public int getTotalUnpaidLeaveDays(Long userId, List<LeaveType> leaveTypes) {
        return leaveRepository.getTotalUnpaidLeaveDays(userId, leaveTypes);
    }

    public List<Leave> getLeavesByReason(String reason) {
        return leaveRepository.findLeavesByReason(reason);
    }

    public List<Leave> getLeavesByUserId(Long userId) {
        return leaveRepository.findLeavesByUserId(userId);
    }

     private int calculateRemainingLeaveDays(LocalDate startDate, LocalDate endDate) {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }
}