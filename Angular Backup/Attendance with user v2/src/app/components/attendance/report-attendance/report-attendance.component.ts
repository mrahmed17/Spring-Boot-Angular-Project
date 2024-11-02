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
  errorMessage: string = '';

  constructor(private attendanceService: AttendanceService) {}

 getUsersWithAttendanceInRange(): void {
    if (!this.startDate || !this.endDate) {
      this.errorMessage = 'Please select a valid date range.';
      return;
    }

    this.attendanceService.getAttendanceInRange(this.startDate, this.endDate).subscribe({
      next: (data: any) => {
        console.log('Fetched Data:', data);
        const map = new Map<UserModel, number>();

        Object.keys(data).forEach((key) => {
          const user: UserModel = this.parseUserFromKey(key);
          const attendanceCount = data[key];
          map.set(user, attendanceCount);
        });

        this.userAttendance = map;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch attendance data. Please try again.';
        console.error(err);
      },
    });
  }

  parseUserFromKey(key: string): UserModel {
    const userPattern = /User\(id=(\d+), fullName=([^,]+), email=([^,]+), contact=([^,]+), role=(\w+)\)/;
    const match = key.match(userPattern);

    if (match) {
      return {
        id: parseInt(match[1], 10),
        fullName: match[2],
        email: match[3],
        contact: match[4],
        role: match[5] as any,
      };
    } else {
      console.error('Failed to parse user from key:', key);
      return {
        id: 0,
        fullName: 'Unknown',
        email: 'unknown',
        contact: 'unknown',
        role: 'UNKNOWN' as any,
      };
    }
  
  }
  
  //   this.attendanceService
  //     .getAttendanceInRange(this.startDate, this.endDate)
  //     .subscribe({
  //       next: (data) => {
  //         console.log('Fetched Data:', data);
  //         this.userAttendance = data; 
  //         this.errorMessage = ''; 
  //       },
  //       error: (err) => {
  //         this.errorMessage =
  //           'Failed to fetch attendance data. Please try again.';
  //         console.error(err);
  //       },
  //     });
  // }

  // attendance: AttendanceModel | null = null;
  // attendances: AttendanceModel[] = [];
  // userAttendances: Map<UserModel, number> | null = null;
  // errorMessage: string | null = null;

  // constructor(private attendanceService: AttendanceService) {}

  // getAttendanceById(id: number): void {
  //   this.attendanceService.getAttendanceById(id).subscribe({
  //     next: (data) => (this.attendance = data),
  //     error: (error) => (this.errorMessage = `Error: ${error.message}`),
  //   });
  // }

  // getAttendancesByUserId(id: number): void {
  //   this.attendanceService.getAttendancesByUserId(id).subscribe({
  //     next: (data) => (this.attendances = data),
  //     error: (error) => (this.errorMessage = `Error: ${error.message}`),
  //   });
  // }

  // getUsersWithAttendanceInRange(startDate: string, endDate: string): void {
  //   this.attendanceService
  //     .getAttendanceInRange(startDate, endDate)
  //     .subscribe({
  //       next: (data) => (this.userAttendances = data),
  //       error: (error) => (this.errorMessage = `Error: ${error.message}`),
  //     });
  // }
}