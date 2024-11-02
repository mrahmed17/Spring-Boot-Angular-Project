import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance.service';
import { NotificationService } from '../../../services/notification.service';
import { faCalendarAlt, faClock, faIdBadge, faList, faSignInAlt, faSignOutAlt, faUser } from '@fortawesome/free-solid-svg-icons';
import { AttendanceModel } from '../../../models/attendance.model';
import { UserModel } from '../../../models/user.model';


@Component({
  selector: 'app-create-attendance',
  templateUrl: './create-attendance.component.html',
  styleUrl: './create-attendance.component.css',
})
export class CreateAttendanceComponent implements OnInit {
  users: UserModel[] = [];
  attendances: AttendanceModel[] = [];
  selectedUserId: number | null = null;
  todaysAttendance: AttendanceModel[] = [];
  errorMessage: string = '';

  // users: any[] = [];
  // attendances: any[] = [];
  // selectedUserId: number | null = null;
  // todaysAttendance: AttendanceModel[] = [];
  // errorMessage: string = '';

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
    // this.loadTodaysAttendance();
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

  // loadTodaysAttendance(): void {
  //   this.attendanceService.getTodaysAttendance().subscribe({
  //     next: (data) => {
  //       this.todaysAttendance = data;
  //       this.errorMessage = '';
  //     },
  //     error: (err) => {
  //       this.errorMessage = "Failed to load today's attendance";
  //       console.error(err);
  //     },
  //   });
  // }

  loadAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
        console.log('Received Attendances:', this.attendances);
      },
      error: (error) => {
        console.error('Error loading attendances:', error);
        // Log raw response for debugging
        if (error.error) {
          console.error('Raw error response:', error.error);
        }
      },
    });
  }

  // checkIn(): void {
  //   if (this.selectedUserId) {
  //     this.attendanceService.checkIn(this.selectedUserId).subscribe({
  //       next: () => {
  //         this.loadTodaysAttendance();
  //         // alert('Check in created successfully.');
  //         this.notificationService.showNotify(
  //           'Check in created successfully.',
  //           'success'
  //         );
  //       },
  //       error: (error) => {
  //         this.handleError('Error during check-in', error);
  //       },
  //     });
  //   }
  // }

  // checkOut(): void {
  //   if (this.selectedUserId) {
  //     this.attendanceService.checkOut(this.selectedUserId).subscribe({
  //       next: (data) => {
  //         this.loadTodaysAttendance();
  //         // alert('Check out created successfully.');
  //         this.notificationService.showNotify(
  //           'Check out created successfully.',
  //           'success'
  //         );
  //       },
  //       error: (error) => {
  //         this.handleError('Error during check-out', error);
  //       },
  //     });
  //   }
  // }

  checkIn(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkIn(this.selectedUserId).subscribe({
        next: () => {
          this.loadAttendances();
          // alert('Check in created successfully.');
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
        next: (data) => {
          this.loadAttendances();
          // alert('Check out created successfully.');
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
