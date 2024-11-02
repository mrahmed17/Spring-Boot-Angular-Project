import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { AttendanceService } from '../../../services/attendance.service';
import { faCalendarAlt, faCalendarDay, faClock, faExclamationTriangle, faIdBadge, faListUl, faSearch, faTable, faUser } from '@fortawesome/free-solid-svg-icons';
import { AttendanceModel } from '../../../models/attendance.model';


@Component({
  selector: 'app-report-attendance',
  templateUrl: './report-attendance.component.html',
  styleUrl: './report-attendance.component.css',
})
export class ReportAttendanceComponent implements OnInit {
  faCalendarDay = faCalendarDay;
  faCalendarAlt = faCalendarAlt;
  faSearch = faSearch;
  faTable = faTable;
  faUser = faUser;
  faListUl = faListUl;
  faExclamationTriangle = faExclamationTriangle;
  faIdBadge = faIdBadge;
  faClock = faClock;
  

  startDate!: string;
  endDate!: string;
  name!: string;
  role!: string;
  selectedUserId: number | null = null;
  selectedStatus: string = '';
  users: UserModel[] = [];
  attendances: AttendanceModel[] = [];
  userAttendance: Map<UserModel, number> = new Map();
  peakAttendanceDay: any[] = [];
  peakAttendanceMonth: any[] = [];
  peakAttendanceYear: any[] = [];
  holidayAttendance: any[] = [];
  lateCheckIns: AttendanceModel[] = [];
  regularEmployees: any[] = [];
  errorMessage: string = '';

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.loadAllUsers();
    this.loadAllAttendances();
  }

  loadAllUsers(): void {
    this.attendanceService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch users.';
        console.error(err);
      },
    });
  }

  loadAllAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch attendances.';
        console.error(err);
      },
    });
  }

  getUsersWithAttendanceInRange(): void {
    if (!this.startDate || !this.endDate) {
      this.errorMessage = 'Please select a valid date range.';
      return;
    }

    this.attendanceService
      .getAttendanceInRange(this.startDate, this.endDate)
      .subscribe({
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
          this.errorMessage =
            'Failed to fetch attendance data. Please try again.';
          console.error(err);
        },
      });
  }

  // applyFilters(): void {
  //   if (!this.startDate || !this.endDate) {
  //     this.errorMessage = 'Please select a valid date range.';
  //     return;
  //   }

  //   this.attendanceService
  //     .getAttendanceInRange(this.startDate, this.endDate)
  //     .subscribe({
  //       next: (data: any) => {
  //         const map = new Map<UserModel, number>();

  //         Object.keys(data).forEach((key) => {
  //           const user: UserModel = this.parseUserFromKey(key);
  //           const attendanceCount = data[key];
  //           map.set(user, attendanceCount);
  //         });

  //         this.userAttendance = map;
  //         this.errorMessage = '';
  //       },
  //       error: (err) => {
  //         this.errorMessage =
  //           'Failed to fetch attendance data. Please try again.';
  //         console.error(err);
  //       },
  //     });
  // }

  parseUserFromKey(key: string): UserModel {
    // Parses the user string as per your backend response format
    const userPattern =
      /User\(id=(\d+), fullName=([^,]+), email=([^,]+), contact=([^,]+), role=(\w+)\), password=(\w+)\)/;
    const match = key.match(userPattern);

    if (match) {
      return {
        id: parseInt(match[1], 10),
        fullName: match[2],
        email: match[3],
        contact: match[4],
        role: match[5] as any,
        password: match[6] as any,
      };
    } else {
      console.error('Failed to parse user from key:', key);
      return {
        id: 0,
        fullName: 'Unknown',
        email: 'unknown',
        contact: 'unknown',
        role: 'UNKNOWN' as any,
        password: 'UNKNOWN' as any,
      };
    }
  }

  resetFilters(): void {
    this.startDate = '';
    this.endDate = '';
    this.selectedUserId = null;
    this.selectedStatus = '';
    this.loadAllAttendances();
  }

  // Fetch peak attendance by day
  getPeakAttendanceDay(): void {
    this.attendanceService.getPeakAttendanceDay().subscribe({
      next: (data) => (this.peakAttendanceDay = data),
      error: (err) => {
        this.errorMessage =
          'Failed to fetch peak attendance by day. Please try again.';
        console.error(err);
      },
    });
  }

  // Fetch peak attendance by month
  getPeakAttendanceMonth(): void {
    this.attendanceService.getPeakAttendanceMonth().subscribe({
      next: (data) => (this.peakAttendanceMonth = data),
      error: (err) => {
        this.errorMessage =
          'Failed to fetch peak attendance by month. Please try again.';
        console.error(err);
      },
    });
  }

  // Fetch peak attendance by year
  getPeakAttendanceYear(): void {
    this.attendanceService.getPeakAttendanceYear().subscribe({
      next: (data) => (this.peakAttendanceYear = data),
      error: (err) => {
        this.errorMessage =
          'Failed to fetch peak attendance by year. Please try again.';
        console.error(err);
      },
    });
  }

  // Fetch holiday attendance
  getHolidayAttendance(holidayDates: string[]): void {
    this.attendanceService.getHolidayAttendance(holidayDates).subscribe({
      next: (data) => (this.holidayAttendance = data),
      error: (err) => {
        this.errorMessage =
          'Failed to fetch holiday attendance. Please try again.';
        console.error(err);
      },
    });
  }

  // Fetch late check-ins
  getLateCheckIns(lateTime: string): void {
    this.attendanceService
      .getLateCheckIns(lateTime, this.startDate, this.endDate)
      .subscribe({
        next: (data) => (this.lateCheckIns = data),
        error: (err) => {
          this.errorMessage =
            'Failed to fetch late check-ins. Please try again.';
          console.error(err);
        },
      });
  }

  // Fetch regular employees for shift planning
  getRegularEmployeesForShiftPlanning(): void {
    this.attendanceService
      .getRegularEmployeesForShiftPlanning(this.startDate, this.endDate)
      .subscribe({
        next: (data) => (this.regularEmployees = data),
        error: (err) => {
          this.errorMessage =
            'Failed to fetch regular employees for shift planning. Please try again.';
          console.error(err);
        },
      });
  }

  // Fetch attendance by user name
  searchAttendancesByUserName(): void {
    if (!this.name) {
      this.errorMessage = 'Please provide a name to search.';
      return;
    }

    this.attendanceService
      .searchAttendancesByUserNamePart(this.name)
      .subscribe({
        next: (data: AttendanceModel[]) => {
          const map = new Map<UserModel, number>();

          // Assuming AttendanceModel has a user and count property
          data.forEach((attendance) => {
            const user: UserModel = attendance.user;
            const count: number = map.get(user) || 0;
            map.set(user, count + 1); // Aggregate attendance count
          });

          this.userAttendance = map;
          this.errorMessage = '';
        },
        error: (err) => {
          this.errorMessage =
            'Failed to search attendances by user name. Please try again.';
          console.error(err);
        },
      });
  }

  // Fetch attendance by role and date range
  searchAttendanceByRoleAndDateRange(): void {
    if (!this.role || !this.startDate || !this.endDate) {
      this.errorMessage = 'Please provide role and date range.';
      return;
    }

    this.attendanceService
      .getAttendanceByRoleAndDateRange(this.role, this.startDate, this.endDate)
      .subscribe({
        next: (data: AttendanceModel[]) => {
          const map = new Map<UserModel, number>();

          data.forEach((attendance) => {
            const user: UserModel = attendance.user;
            const count: number = map.get(user) || 0;
            map.set(user, count + 1); // Aggregate attendance count
          });

          this.userAttendance = map;
          this.errorMessage = '';
        },
        error: (err) => {
          this.errorMessage =
            'Failed to search attendance by role and date range. Please try again.';
          console.error(err);
        },
      });
  }
}