import { Component } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { AttendanceService } from '../../../services/attendance.service';

@Component({
  selector: 'app-create-attendance',
  templateUrl: './create-attendance.component.html',
  styleUrl: './create-attendance.component.css'
})
export class CreateAttendanceComponent {

  attendance: AttendanceModel = new AttendanceModel();

  constructor(private attendanceService: AttendanceService) { }

  saveAttendance() {
    this.attendanceService.saveAttendance(this.attendance).subscribe(
      (response) => {
        console.log('Attendance saved successfully', response);
        alert('Attendance saved successfully');
      },
      (error) => {
        console.error('Error saving attendance', error);
        alert('Error saving attendance');
      }
    );
  }
}