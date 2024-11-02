import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AttendanceModel } from '../models/attendance.model';
import { UserModel } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AttendanceService {
  private baseUrl: string = `${environment.apiUrl}/attendance`;

  constructor(private http: HttpClient) {}

  getTodaysAttendance(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/todayAttendance`);
  }

  getAllAttendances(): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(`${this.baseUrl}/all`);
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.baseUrl}/allUsers`);
  }

  checkIn(userId: number): Observable<AttendanceModel> {
    return this.http.post<AttendanceModel>(`${this.baseUrl}/checkin`, {
      userId,
    });
  }

  checkOut(userId: number): Observable<AttendanceModel> {
    return this.http.put<AttendanceModel>(`${this.baseUrl}/checkout`, {
      userId,
    });
  }

  getAttendanceById(id: number): Observable<AttendanceModel> {
    return this.http.get<AttendanceModel>(`${this.baseUrl}/find/${id}`);
  }

  getAttendancesByUserId(userId: number): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/user/${userId}/attendances`
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
      `${this.baseUrl}/attendanceRange`,
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

  getTodayAttendanceByUserId(userId: number): Observable<AttendanceModel[]> {
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/todayAttendance/${userId}`
    );
  }

  getAttendanceHistoryForUser(
    userId: number,
    startDate: string,
    endDate: string
  ): Observable<AttendanceModel[]> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<AttendanceModel[]>(
      `${this.baseUrl}/attendance-history`,
      { params }
    );
  }

  getUsersWithAttendanceInRange(
    startDate: string,
    endDate: string
  ): Observable<Map<UserModel, number>> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<Map<UserModel, number>>(
      `${this.baseUrl}/attendanceRange`,
      { params }
    );
  }

  getEmployeesWithHighLeaveRate(threshold: number): Observable<UserModel[]> {
    const params = new HttpParams().set('threshold', threshold.toString());
    return this.http.get<UserModel[]>(`${this.baseUrl}/high-leave-rate`, {
      params,
    });
  }

  findUsersWithoutAttendanceToday(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(
      `${this.baseUrl}/users-without-attendance`
    );
  }
}
