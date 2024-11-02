import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';

@Component({
  selector: 'app-leave-approval',
  templateUrl: './leave-approval.component.html',
  styleUrls: ['./leave-approval.component.css'],
})
export class LeaveApprovalComponent implements OnInit {
  pendingLeaves: LeaveModel[] = [];

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.getPendingLeaves();
  }

  // Fetch all pending leave requests
  getPendingLeaves() {
    this.leaveService.getPendingLeaveRequests().subscribe({
      next: (leaves) => {
        this.pendingLeaves = leaves;
      },
      error: (error) => {
        console.error('Error fetching pending leaves', error);
      },
    });
  }

  // Approve a leave
  approveLeave(leaveId: number) {
    this.leaveService.approveLeave(leaveId).subscribe({
      next: (approvedLeave) => {
        this.removeLeaveFromList(leaveId);
        alert('Leave approved successfully.');
      },
      error: (error) => {
        console.error('Error approving leave', error);
        alert('Error while approving leave.');
      },
    });
  }

  // Reject a leave
  rejectLeave(leaveId: number) {
    this.leaveService.rejectLeave(leaveId).subscribe({
      next: (rejectedLeave) => {
        this.removeLeaveFromList(leaveId);
        alert('Leave rejected successfully.');
      },
      error: (error) => {
        console.error('Error rejecting leave', error);
        alert('Error while rejecting leave.');
      },
    });
  }

  // Remove leave from the list after approval or rejection
  private removeLeaveFromList(leaveId: number) {
    this.pendingLeaves = this.pendingLeaves.filter(
      (leave) => leave.id !== leaveId
    );
  }
}
