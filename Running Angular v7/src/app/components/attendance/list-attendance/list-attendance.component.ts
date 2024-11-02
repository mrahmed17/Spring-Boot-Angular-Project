import { Component, OnInit } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { AttendanceService } from '../../../services/attendance.service';
import {
  faCalendarAlt,
  faClock,
  faIdBadge,
  faListUl,
} from '@fortawesome/free-solid-svg-icons';
import { UserModel } from '../../../models/user.model';
import { RoleEnum } from '../../../models/role.enum';

@Component({
  selector: 'app-list-attendance',
  templateUrl: './list-attendance.component.html',
  styleUrls: ['./list-attendance.component.css'],
})
export class ListAttendanceComponent implements OnInit {
  faListUl = faListUl;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faClock = faClock;

  attendances: AttendanceModel[] = [];
  filteredAttendances: AttendanceModel[] = [];
  searchTerm: string = ''; // Search term for user names

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.getAllAttendances();
  }

  getAllAttendances() {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
        this.filteredAttendances = data; // Initialize filtered list
      },
      error: (error) => {
        console.error('Error fetching attendances', error);
      },
    });
  }

  // Method to filter attendances based on search term
  filterAttendances() {
    if (this.searchTerm) {
      this.filteredAttendances = this.attendances.filter((attendance) =>
        attendance.user.fullName
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.filteredAttendances = this.attendances; // Reset to all attendances if search term is empty
    }
  }
}
