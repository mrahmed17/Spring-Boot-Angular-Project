import { Component, OnInit } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { ActivatedRoute } from '@angular/router';
import { AttendanceService } from '../../../services/attendance.service';

@Component({
  selector: 'app-view-attendance',
  templateUrl: './view-attendance.component.html',
  styleUrl: './view-attendance.component.css',
})
export class ViewAttendanceComponent implements OnInit {
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