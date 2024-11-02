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
  private baseUrl: string = `${environment.apiUrl}/attendance`;

  // private baseUrl = 'http://localhost:8080/api/attendance';

  constructor(private http: HttpClient) {}

  getTodaysAttendance(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/today`);
  }

  getAllAttendances(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/`);
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

  getAttendanceById(id: number): Observable<AttendanceModel> {
    return this.http.get<AttendanceModel>(`${this.baseUrl}/attendance/${id}`);
  }

  getAttendancesByUserId(id: number): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/user/${id}/attendances`
    );
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

  getPeakAttendanceDay(): Observable<Object[]> {
    return this.http.get<Object[]>(`${this.baseUrl}/peakAttendanceDay`);
  }

  getPeakAttendanceMonth(): Observable<Object[]> {
    return this.http.get<Object[]>(`${this.baseUrl}/peakAttendanceMonth`);
  }

  getPeakAttendanceYear(): Observable<Object[]> {
    return this.http.get<Object[]>(`${this.baseUrl}/peakAttendanceYear`);
  }

  getHolidayAttendance(holidayDates: string[]): Observable<Object[]> {
    return this.http.post<Object[]>(
      `${this.baseUrl}/holidayAttendance`,
      holidayDates
    );
  }

  getLateCheckIns(
    lateTime: string,
    startDate: string,
    endDate: string
  ): Observable<AttendanceModel[]> {
    const params = new HttpParams()
      .set('lateTime', lateTime)
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/lateCheckIns`, {
      params,
    });
  }

  getRegularEmployeesForShiftPlanning(
    startDate: string,
    endDate: string
  ): Observable<Object[]> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<Object[]>(`${this.baseUrl}/shiftPlanning`, { params });
  }

  searchAttendancesByUserNamePart(name: string): Observable<AttendanceModel[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/searchByName`, {
      params,
    });
  }

  getAttendanceByRoleAndDateRange(
    role: string,
    startDate: string,
    endDate: string
  ): Observable<AttendanceModel[]> {
    const params = new HttpParams()
      .set('role', role)
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/roleAttendance`, {
      params,
    });
  }

  getTodayAttendanceByUserId(userId: number): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/todayAttendance/${userId}`
    );
  }
}
