import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance.service';
import { NotificationService } from '../../../services/notification.service';
import {
  faCalendarAlt,
  faClock,
  faIdBadge,
  faList,
  faSignInAlt,
  faSignOutAlt,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import { AttendanceModel } from '../../../models/attendance.model';
import { UserModel } from '../../../models/user.model';

@Component({
  selector: 'app-create-attendance',
  templateUrl: './create-attendance.component.html',
  styleUrls: ['./create-attendance.component.css'], // Corrected 'styleUrl' to 'styleUrls'
})
export class CreateAttendanceComponent implements OnInit {
  users: UserModel[] = [];
  todaysAttendance: AttendanceModel[] = [];
  selectedUserId: number | null = null;
  errorMessage: string = '';

  faUser = faUser;
  faSignIn = faSignInAlt;
  faSignOut = faSignOutAlt;
  faList = faList;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faClock = faClock;

  constructor(
    private attendanceService: AttendanceService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadTodaysAttendance();
  }

  loadUsers(): void {
    this.attendanceService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Error loading users:', error);
      },
    });
  }

  loadTodaysAttendance(): void {
    this.attendanceService.getTodaysAttendance().subscribe({
      next: (data) => {
        this.todaysAttendance = data;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = "Failed to load today's attendance";
        console.error(err);
      },
    });
  }

  checkIn(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkIn(this.selectedUserId).subscribe({
        next: () => {
          this.loadTodaysAttendance(); // Refresh only today's attendance
          this.notificationService.showNotify(
            'Check in created successfully.',
            'success'
          );
        },
        error: (error) => {
          this.handleError('Error during check-in', error);
        },
      });
    }
  }

  checkOut(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkOut(this.selectedUserId).subscribe({
        next: () => {
          this.loadTodaysAttendance(); // Refresh only today's attendance
          this.notificationService.showNotify(
            'Check out created successfully.',
            'success'
          );
        },
        error: (error) => {
          this.handleError('Error during check-out', error);
        },
      });
    }
  }

  private handleError(message: string, error: any): void {
    this.errorMessage = message;
    console.error(message, error);
    this.notificationService.showNotify(message, 'error');
  }
}
