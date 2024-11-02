import { Component, OnInit } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { AttendanceService } from '../../../services/attendance.service';
import { faCalendarAlt, faClock, faIdBadge, faListUl } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-list-attendance',
  templateUrl: './list-attendance.component.html',
  styleUrl: './list-attendance.component.css',
})
export class ListAttendanceComponent implements OnInit {
  faListUl = faListUl;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faClock = faClock;

  attendances: AttendanceModel[] = [];

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.getAllAttendances();
  }

  getAllAttendances() {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
      },
      error: (error) => {
        console.error('Error fetching attendances', error);
      },
    });
  }
}