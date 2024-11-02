import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { UserModel } from '../../../models/user.model';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';


@Component({
  selector: 'app-leave-summary',
  templateUrl: './leave-summary.component.html',
  styleUrls: ['./leave-summary.component.css'],
})
export class LeaveSummaryComponent implements OnInit {
  user!: UserModel; // Assuming you have a user model to store the logged-in user
  leaveSummary: {
    leaveType: string;
    totalDays: number;
    remainingDays: number;
  }[] = [];
  errorMessage: string = '';

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.loadLeaveSummary();
  }

  // Function to load leave summary for the current user
  loadLeaveSummary() {
    const userId = this.user?.id; // Make sure the user is defined

    if (userId) {
      // Fetch leave summary for the user
      this.leaveService.getCurrentYearLeaves(userId).subscribe({
        next: (leaves: LeaveModel[]) => {
          this.calculateLeaveSummary(leaves);
        },
        error: (error) => {
          console.error('Error fetching leave summary', error);
          this.errorMessage = 'Unable to load leave summary.';
        },
      });
    } else {
      this.errorMessage = 'User not logged in or user ID is undefined.';
    }
  }

  // Helper function to calculate leave summary based on leave data
  calculateLeaveSummary(leaves: LeaveModel[]) {
    const summary = new Map<
      string,
      { totalDays: number; remainingDays: number }
    >();

    leaves.forEach((leave) => {
      // Convert leaveType (LeaveTypeEnum) to a string
      const leaveType = LeaveTypeEnum[leave.leaveType];

      // Convert startDate and endDate (Date objects) to strings in 'YYYY-MM-DD' format
      const startDateString = leave.startDate.toISOString().split('T')[0];
      const endDateString = leave.endDate.toISOString().split('T')[0];

      // Calculate total days
      const totalDays = this.calculateTotalDays(startDateString, endDateString);

      if (!summary.has(leaveType)) {
        summary.set(leaveType, {
          totalDays: 0,
          remainingDays: leave.remainingLeave,
        });
      }

      const currentSummary = summary.get(leaveType)!;
      currentSummary.totalDays += totalDays;
      summary.set(leaveType, currentSummary);
    });

    this.leaveSummary = Array.from(summary.entries()).map(
      ([leaveType, { totalDays, remainingDays }]) => ({
        leaveType,
        totalDays,
        remainingDays,
      })
    );
  }


  // Helper function to calculate the number of days between two dates
  calculateTotalDays(startDate: string, endDate: string): number {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const timeDiff = end.getTime() - start.getTime();
    return Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
  }
}
