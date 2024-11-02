import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AttendanceModel } from '../models/attendance.model';
import { Observable } from 'rxjs';
import { UserModel } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AttendanceService {
  private baseUrl = 'http://localhost:8080/api/attendance';

  constructor(private http: HttpClient) {}

  getAllAttendances(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/`);
  }

  getAttendanceById(id: number): Observable<AttendanceModel> {
    return this.http.get<AttendanceModel>(`${this.baseUrl}/view/${id}`);
  }

   saveAttendance(attendance: AttendanceModel): Observable<AttendanceModel> {
    return this.http.post<AttendanceModel>(`${this.baseUrl}/save`, attendance);
  }

  
  getAttendanceInRange(
    startDate: string,
    endDate: string
  ): Observable<Map<UserModel, number>> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<Map<UserModel, number>>(
      `${this.baseUrl}/attendance-with-range`,
      { params }
    );
  }
}