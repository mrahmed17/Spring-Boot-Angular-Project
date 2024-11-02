import { Component } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { AttendanceService } from '../../../services/attendance.service';

@Component({
  selector: 'app-report-attendance',
  templateUrl: './report-attendance.component.html',
  styleUrl: './report-attendance.component.css',
})
export class ReportAttendanceComponent {
  startDate!: string;
  endDate!: string;
  userAttendance: Map<UserModel, number> = new Map();

  constructor(private attendanceService: AttendanceService) {}

  getAttendanceInRange() {
    this.attendanceService
      .getAttendanceInRange(this.startDate, this.endDate)
      .subscribe({
        next: data => {
          this.userAttendance = data;
        },
        error: error => {
          console.error('Error fetching attendance in range', error);
        }
      });
  }
}