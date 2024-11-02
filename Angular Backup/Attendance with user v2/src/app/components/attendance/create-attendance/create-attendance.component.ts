import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance.service';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-create-attendance',
  templateUrl: './create-attendance.component.html',
  styleUrl: './create-attendance.component.css',
})
export class CreateAttendanceComponent implements OnInit {
  users: any[] = []; 
  attendances: any[] = []; 
  selectedUserId: number | null = null;

  constructor(
    private attendanceService: AttendanceService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadAttendances();
  }

  loadUsers(): void {
    this.attendanceService.getAllUsers().subscribe(
      {
        next:(data) => {
        this.users = data;
      },
     error: (error) => {
        console.error('Error loading users:', error);
      }
      }
    );
  }

  loadAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe(
      {
        next: (data) => {
          this.attendances = data;
           console.log('Attendance Data:', data);
        },
        error: (error) => {
          console.error('Error loading attendances:', error);
        }
      });
  }

  checkIn(): void {
    if (this.selectedUserId) {
      this.attendanceService.checkIn(this.selectedUserId).subscribe({
        next: (data) => {
          this.loadAttendances();
          alert('Check in created successfully.');
          this.notificationService.showNotify('Check in created successfully.');
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
            'Check out created successfully.'
          );
        },
        error: (error) => {
          console.error('Error during check-in:', error);
        },
      });
    }
  }
}
