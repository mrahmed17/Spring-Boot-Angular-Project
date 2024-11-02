import { Component, OnInit } from '@angular/core';
import {
  AttendanceService,
  UserAttendanceCount,
} from '../../../services/attendance.service';
import {
  faCalendarDay,
  faExclamationTriangle,
  faSearch,
} from '@fortawesome/free-solid-svg-icons';
import { UserModel } from '../../../models/user.model';

@Component({
  selector: 'app-report-attendance',
  templateUrl: './report-attendance.component.html',
  styleUrls: ['./report-attendance.component.css'],
})
export class ReportAttendanceComponent implements OnInit {
  faCalendarDay = faCalendarDay;
  faExclamationTriangle = faExclamationTriangle;
  faSearch = faSearch;
  errorMessage: string = '';
  attendances: UserAttendanceCount[] = []; // Update type
  filteredAttendance: UserAttendanceCount[] = []; // Update type
  nameSearch: string = '';
  startDate: string = '';
  endDate: string = '';

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    // Optionally load all attendance records initially
    this.loadAttendances();
  }

  loadAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        // Assuming data is in the expected format
        this.attendances = data.map((attendance) => ({
          user: attendance.user,
          attendanceCount: 1, // Default count; modify as necessary
        }));
        this.filteredAttendance = this.attendances;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage =
          'Failed to fetch attendance records. Please try again.';
        console.error(err);
      },
    });
  }

  getAttendanceInRange(): void {
    if (this.startDate && this.endDate) {
      this.attendanceService
        .getAttendanceInRange(this.startDate, this.endDate)
        .subscribe({
          next: (data) => {
            // Convert the Map<UserModel, number> to UserAttendanceCount[]
            this.attendances = Array.from(data.entries()).map(
              ([user, count]) => ({
                user,
                attendanceCount: count,
              })
            );
            this.filteredAttendance = this.attendances; // Now UserAttendanceCount[]
            this.errorMessage = '';
          },
          error: (err) => {
            this.errorMessage =
              'Failed to fetch attendance records for the specified date range.';
            console.error(err);
          },
        });
    } else {
      this.errorMessage = 'Please select both start and end dates.';
    }
  }

  searchByName(): void {
    if (this.nameSearch) {
      this.filteredAttendance = this.attendances.filter((attendance) =>
        attendance.user.fullName
          .toLowerCase()
          .includes(this.nameSearch.toLowerCase())
      );
    } else {
      this.filteredAttendance = this.attendances; // Reset filter
    }
  }

  resetFilters(): void {
    this.nameSearch = '';
    this.filteredAttendance = this.attendances;
    this.startDate = '';
    this.endDate = '';
  }

  viewDetails(attendance: UserAttendanceCount): void {
    alert(
      `Details for ${attendance.user.fullName}: \nAttendance Count: ${attendance.attendanceCount}` // Adjusted for new structure
    );
    console.log('Viewing details for:', attendance);
  }
}
