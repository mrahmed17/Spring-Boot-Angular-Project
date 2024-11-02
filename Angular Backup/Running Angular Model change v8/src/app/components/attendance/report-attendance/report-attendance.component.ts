import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { AttendanceService } from '../../../services/attendance.service';
import { AttendanceModel } from '../../../models/attendance.model';


@Component({
  selector: 'app-report-attendance',
  templateUrl: './report-attendance.component.html',
  styleUrl: './report-attendance.component.css',
})
export class ReportAttendanceComponent implements OnInit {
  todayAttendance: AttendanceModel[] = [];
  peakAttendanceDay: any[] = [];
  peakAttendanceMonth: any[] = [];
  peakAttendanceYear: any[] = [];
  attendanceInRange: Map<UserModel, number> = new Map();
  lateCheckIns: AttendanceModel[] = [];
  usersWithHighLeaveRate: UserModel[] = [];
  usersWithoutAttendanceToday: UserModel[] = [];

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.getTodayAttendance();
    this.getPeakAttendance();
    this.findUsersWithoutAttendanceToday();
  }

  getTodayAttendance(): void {
    const userId = 1;
    this.attendanceService.getTodayAttendanceByUserId(userId).subscribe(
      {
       next: (attendance) => {
        this.todayAttendance = attendance;
      },
     error: (error) => {
        console.error("Error fetching today's attendance:", error);
      }
      }
    );
  }

 
  getPeakAttendance(): void {
    this.attendanceService.getPeakAttendanceDay().subscribe(
      (data) => {
        this.peakAttendanceDay = data;
      },
      (error) => {
        console.error('Error fetching peak attendance for the day:', error);
      }
    );

    this.attendanceService.getPeakAttendanceMonth().subscribe(
      (data) => {
        this.peakAttendanceMonth = data;
      },
      (error) => {
        console.error('Error fetching peak attendance for the month:', error);
      }
    );

    this.attendanceService.getPeakAttendanceYear().subscribe(
      (data) => {
        this.peakAttendanceYear = data;
      },
      (error) => {
        console.error('Error fetching peak attendance for the year:', error);
      }
    );
  }

  findUsersWithoutAttendanceToday(): void {
    this.attendanceService.findUsersWithoutAttendanceToday().subscribe(
      (users) => {
        this.usersWithoutAttendanceToday = users;
      },
      (error) => {
        console.error('Error fetching users without attendance today:', error);
      }
    );
  }

  getAttendanceInRange(startDate: string, endDate: string): void {
    this.attendanceService.getAttendanceInRange(startDate, endDate).subscribe(
      (attendance) => {
        this.attendanceInRange = attendance;
      },
      (error) => {
        console.error('Error fetching attendance in range:', error);
      }
    );
  }

  getLateCheckIns(lateTime: string, startDate: string, endDate: string): void {
    this.attendanceService
      .getLateCheckIns(lateTime, startDate, endDate)
      .subscribe(
        (checkIns) => {
          this.lateCheckIns = checkIns;
        },
        (error) => {
          console.error('Error fetching late check-ins:', error);
        }
      );
  }

  getEmployeesWithHighLeaveRate(threshold: number): void {
    this.attendanceService.getEmployeesWithHighLeaveRate(threshold).subscribe(
      (employees) => {
        this.usersWithHighLeaveRate = employees;
      },
      (error) => {
        console.error('Error fetching employees with high leave rate:', error);
      }
    );
  }
}