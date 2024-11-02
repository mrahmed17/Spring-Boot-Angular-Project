import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AttendanceModel } from '../models/attendance.model';
import { Observable } from 'rxjs';
import { UserModel } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AttendanceService {
  // private baseUrl = 'http://localhost:8080/api/attendance';

  private baseUrl = `${environment.apiUrl}/attendance`;

  constructor(private http: HttpClient) {}

  getAllAttendances(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/`);
  }

  getAttendanceById(id: number): Observable<AttendanceModel> {
    return this.http.get<AttendanceModel>(`${this.baseUrl}/attendance/${id}`);
  }

  getAttendancesByUserId(id: number): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/user/${id}/attendances`
    );
  }
  

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.baseUrl}/allUsers`);
  }

  checkIn(userId: number): Observable<AttendanceModel> {
    const body = { userId };
    return this.http.post<AttendanceModel>(`${this.baseUrl}/checkin`, body);
  }

  checkOut(userId: number): Observable<AttendanceModel> {
    const body = { userId };
    return this.http.put<AttendanceModel>(`${this.baseUrl}/checkout`, body);
  }

  getAttendanceInRange(
    startDate: string,
    endDate: string
  ): Observable<Map<UserModel, number>> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<Map<UserModel, number>>(
      `${this.baseUrl}/attendancerange`,
      { params }
    );
  }
}