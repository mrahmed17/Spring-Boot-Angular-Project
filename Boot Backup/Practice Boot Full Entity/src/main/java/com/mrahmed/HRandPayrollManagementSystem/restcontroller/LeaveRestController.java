//package com.mrahmed.HRandPayrollManagementSystem.restcontroller;
//
//
//import com.mrahmed.HRandPayrollManagementSystem.entity.Leave;
//import com.mrahmed.HRandPayrollManagementSystem.service.LeaveService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/leave")
//@CrossOrigin("*")
//public class LeaveRestController {
//
//    @Autowired
//    private LeaveService leaveService;
//
//    // Admin or Manager approves a leave
//    @PutMapping("/{leaveId}/approve")
//    public ResponseEntity<Leave> approveLeave(@PathVariable long leaveId, @RequestParam long approverId) {
//        Leave approvedLeave = leaveService.approveLeave(leaveId, approverId);
//        return ResponseEntity.ok(approvedLeave);
//    }
//
//    // Admin or Manager rejects a leave
//    @PutMapping("/{leaveId}/reject")
//    public ResponseEntity<Leave> rejectLeave(@PathVariable long leaveId, @RequestParam long approverId) {
//        Leave rejectedLeave = leaveService.rejectLeave(leaveId, approverId);
//        return ResponseEntity.ok(rejectedLeave);
//    }
//
//
//}
