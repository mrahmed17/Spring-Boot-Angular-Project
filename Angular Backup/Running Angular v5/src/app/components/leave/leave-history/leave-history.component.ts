import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { UserModel } from '../../../models/user.model';

@Component({
  selector: 'app-leave-history',
  templateUrl: './leave-history.component.html',
  styleUrls: ['./leave-history.component.css'],
})
export class LeaveHistoryComponent implements OnInit {
  leaveHistory: LeaveModel[] = [];
  user!: UserModel; // Replace with actual logged-in user info

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.getLeaveHistory();
  }

  // getLeaveHistory() {
  //   const userId = this.user?.id; // Optional chaining to safely access the user ID

  //   if (userId) {
  //     // Only call the service if the userId is defined
  //     this.leaveService.getCurrentYearLeaves(userId).subscribe({
  //       next: (history) => {
  //         this.leaveHistory = history;
  //       },
  //       error: (error) => {
  //         console.error('Error fetching leave history', error);
  //       },
  //     });
  //   } else {
  //     console.error('User ID is undefined. Cannot fetch leave history.');
  //   }
  // }

  getLeaveHistory() {
    const userId = this.user?.id ?? 0; // Default to 0 if userId is undefined

    if (userId !== 0) {
      this.leaveService.getCurrentYearLeaves(userId).subscribe({
        next: (history) => {
          this.leaveHistory = history;
        },
        error: (error) => {
          console.error('Error fetching leave history', error);
        },
      });
    } else {
      console.error('User ID is undefined or 0. Cannot fetch leave history.');
    }
  }
}
