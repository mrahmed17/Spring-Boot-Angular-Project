import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { UserModel } from '../../../models/user.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-leave-history',
  templateUrl: './leave-history.component.html',
  styleUrls: ['./leave-history.component.css'],
})
export class LeaveHistoryComponent implements OnInit {
  leaveHistory: LeaveModel[] = [];
  user!: UserModel;

  constructor(
    private leaveService: LeaveService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUserAndHistory();
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

  // Load user data and fetch leave history
  loadUserAndHistory() {
    this.authService.getLoggedInUser().subscribe({
      next: (userData: UserModel) => {
        this.user = userData;
        this.getLeaveHistory();
      },
      error: (error) => {
        console.error('Error fetching user data', error);
      },
    });
  }

  getLeaveHistory() {
    const userId = this.user?.id ?? 0;

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
