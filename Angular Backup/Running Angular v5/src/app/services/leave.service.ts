import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { LeaveModel } from '../models/leave.model';
import { Observable } from 'rxjs';
import { LeaveTypeEnum } from '../models/leave-type.enum';

@Injectable({
  providedIn: 'root',
})
export class LeaveService {
  private baseUrl: string = environment.apiUrl + '/leaves';

  constructor(private http: HttpClient) {}

  // Save a leave request
  applyLeave(leave: LeaveModel): Observable<LeaveModel> {
    return this.http.post<LeaveModel>(`${this.baseUrl}/save`, leave);
  }
  
  // update a leave request
  updateLeave(leave: LeaveModel): Observable<LeaveModel> {
    return this.http.post<LeaveModel>(`${this.baseUrl}/update/{id}`, leave);
  }

  // Delete a leave request by ID
  deleteLeave(leaveId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${leaveId}`);
  }

  // Get leave by ID
  getLeaveById(leaveId: number): Observable<LeaveModel> {
    return this.http.get<LeaveModel>(`${this.baseUrl}/find/${leaveId}`);
  }

  // Approve a leave request by ID
  approveLeave(leaveId: number): Observable<LeaveModel> {
    return this.http.post<LeaveModel>(
      `${this.baseUrl}/approve/${leaveId}`,
      null
    );
  }

  // Reject a leave request by ID
  rejectLeave(leaveId: number): Observable<LeaveModel> {
    return this.http.post<LeaveModel>(`${this.baseUrl}/reject/${leaveId}`, null);
  }

  // Get all leaves for a specific user in a year
  getLeavesByUserAndYear(
    userId: number,
    year: number
  ): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(
      `${this.baseUrl}/user/${userId}/year/${year}`
    );
  }

  // Get pending leave requests
  getPendingLeaveRequests(): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(`${this.baseUrl}/pending`);
  }

  // Get leaves for a specific month and year
  getLeavesByMonthAndYear(
    month: string,
    year: number
  ): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(
      `${this.baseUrl}/month/${month}/year/${year}`
    );
  }

  // Get leaves by leave type
  getLeavesByType(leaveType: LeaveTypeEnum): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(`${this.baseUrl}/type/${leaveType}`);
  }

  // Get approved leaves for a specific user
  getApprovedLeavesByUser(userId: number): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(
      `${this.baseUrl}/user/${userId}/approved`
    );
  }

  // Get leaves for a user in a specific date range
  getLeavesByUserAndDateRange(
    userId: number,
    startDate: Date,
    endDate: Date
  ): Observable<LeaveModel[]> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString().split('T')[0])
      .set('endDate', endDate.toISOString().split('T')[0]);
    return this.http.get<LeaveModel[]>(`${this.baseUrl}/user/${userId}/range`, {
      params,
    });
  }

  // Calculate remaining leaves for a user in a specific year
  calculateRemainingLeaves(userId: number, year: number): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/user/${userId}/remaining/year/${year}`
    );
  }

  // Get total unpaid leave days for a user in a specific year
  getTotalUnpaidLeaveDays(
    userId: number,
    leaveTypes: LeaveTypeEnum[],
    year: number
  ): Observable<number> {
    const params = new HttpParams()
      .set('leaveTypes', leaveTypes.join(','))
      .set('year', year.toString());
    return this.http.get<number>(
      `${this.baseUrl}/user/${userId}/unpaid/year/${year}`,
      { params }
    );
  }

  // Get current year leaves for a specific user
  getCurrentYearLeaves(userId: number): Observable<LeaveModel[]> {
    return this.http.get<LeaveModel[]>(`${this.baseUrl}/user/${userId}/current`);
  }
}
