import { Component, OnInit } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { ActivatedRoute } from '@angular/router';
import { AttendanceService } from '../../../services/attendance.service';
import { faCalendarAlt, faClock, faEye, faIdBadge } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-view-attendance',
  templateUrl: './view-attendance.component.html',
  styleUrl: './view-attendance.component.css',
})
export class ViewAttendanceComponent implements OnInit {
  faEye = faEye;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faClock = faClock;

  attendance!: AttendanceModel;

  constructor(
    private route: ActivatedRoute,
    private attendanceService: AttendanceService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.getAttendanceById(id);
  }

  getAttendanceById(id: number) {
    this.attendanceService.getAttendanceById(id).subscribe(
      (data) => {
        this.attendance = data;
      },
      (error) => {
        console.error('Error fetching attendance by ID', error);
      }
    );
  }
}