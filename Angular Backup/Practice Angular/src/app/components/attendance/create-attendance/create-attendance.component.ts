import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance.service';
import { NotificationService } from '../../../services/notification.service';
import { faCalendarAlt, faClock, faIdBadge, faList, faSignInAlt, faSignOutAlt, faUser } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-create-attendance',
  templateUrl: './create-attendance.component.html',
  styleUrl: './create-attendance.component.css',
})
export class CreateAttendanceComponent implements OnInit {
  users: any[] = [];
  attendances: any[] = [];
  selectedUserId: number | null = null;

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
    this.loadAttendances();
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

  loadAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
        console.log('Attendance Data:', data);
      },
      error: (error) => {
        console.error('Error loading attendances:', error);
      },
    });
  }

  checkIn(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkIn(this.selectedUserId).subscribe({
        next: (data) => {
          this.loadAttendances();
          alert('Check in created successfully.');
          this.notificationService.showNotify(
            'Check in created successfully.',
            'success'
          );
        },
        error: (error) => {
          console.error('Error during check-in:', error);
        },
      });
    }
  }

  checkOut(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkOut(this.selectedUserId).subscribe({
        next: (data) => {
          this.loadAttendances();
          alert('Check out created successfully.');
          this.notificationService.showNotify(
            'Check out created successfully.',
            'success'
          );
        },
        error: (error) => {
          console.error('Error during check-in:', error);
        },
      });
    }
  }
}
