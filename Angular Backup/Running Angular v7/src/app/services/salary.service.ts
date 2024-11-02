import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { SalaryModel } from '../models/salary.model';
import { MonthEnum } from '../models/month.enum';

@Injectable({
  providedIn: 'root',
})
export class SalaryService {
  private baseUrl: string = `${environment.apiUrl}/salaries`;

  constructor(private http: HttpClient) {}

  createSalary(salary: SalaryModel): Observable<SalaryModel> {
    return this.http.post<SalaryModel>(`${this.baseUrl}/create`, salary);
  }

  updateSalary(salaryId: number, salary: SalaryModel): Observable<SalaryModel> {
    return this.http.put<SalaryModel>(
      `${this.baseUrl}/update/${salaryId}`,
      salary
    );
  }

  deleteSalary(salaryId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${salaryId}`);
  }

  getSalaryById(salaryId: number): Observable<SalaryModel> {
    return this.http.get<SalaryModel>(`${this.baseUrl}/find/${salaryId}`);
  }

  getSalariesByUserAndYear(
    userId: number,
    year: number
  ): Observable<SalaryModel[]> {
    return this.http.get<SalaryModel[]>(
      `${this.baseUrl}/user/${userId}/year/${year}`
    );
  }

  getSalariesByUserYearAndMonth(
    userId: number,
    year: number,
    month: MonthEnum
  ): Observable<SalaryModel[]> {
    return this.http.get<SalaryModel[]>(
      `${this.baseUrl}/user/${userId}/year/${year}/month/${month}`
    );
  }

  getLatestSalaryByUser(userId: number): Observable<SalaryModel[]> {
    return this.http.get<SalaryModel[]>(
      `${this.baseUrl}/user/${userId}/latest`
    );
  }

  getTotalSalaryByUserAndYear(
    userId: number,
    year: number
  ): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/user/${userId}/year/${year}/total`
    );
  }

  getSalariesByDateRange(
    startDate: Date,
    endDate: Date
  ): Observable<SalaryModel[]> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());
    return this.http.get<SalaryModel[]>(`${this.baseUrl}/range`, { params });
  }

  calculateTotalSalary(
    userId: number,
    startDate: Date,
    endDate: Date
  ): Observable<number> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());
    return this.http.get<number>(`${this.baseUrl}/user/${userId}/calculate`, {
      params,
    });
  }

  getOvertimeSalary(
    userId: number,
    startDate: Date,
    endDate: Date
  ): Observable<number> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());
    return this.http.get<number>(`${this.baseUrl}/user/${userId}/overtime`, {
      params,
    });
  }

  getTotalOvertimeHours(
    userId: number,
    startDate: Date,
    endDate: Date
  ): Observable<number> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());
    return this.http.get<number>(
      `${this.baseUrl}/user/${userId}/overtime-hours`,
      { params }
    );
  }
}
