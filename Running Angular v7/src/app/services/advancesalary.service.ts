import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdvanceSalaryModel } from '../models/advance-salary.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdvanceSalaryService {
  private baseUrl = `${environment.apiUrl}/advanceSalaries`;

  constructor(private http: HttpClient) {}

  // Create a new advance salary
  createAdvanceSalary(
    advanceSalary: AdvanceSalaryModel
  ): Observable<AdvanceSalaryModel> {
    return this.http.post<AdvanceSalaryModel>(
      `${this.baseUrl}/create`,
      advanceSalary
    );
  }

  // Update an existing advance salary
  updateAdvanceSalary(
    id: number,
    advanceSalary: AdvanceSalaryModel
  ): Observable<AdvanceSalaryModel> {
    return this.http.put<AdvanceSalaryModel>(
      `${this.baseUrl}/update/${id}`,
      advanceSalary
    );
  }

  // Delete an advance salary by ID
  deleteAdvanceSalary(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  // Get an advance salary by ID
  getAdvanceSalaryById(id: number): Observable<AdvanceSalaryModel> {
    return this.http.get<AdvanceSalaryModel>(`${this.baseUrl}/find/${id}`);
  }

  // Get advance salaries by user and year
  getAdvanceSalariesByUserAndYear(
    userId: number,
    year: number
  ): Observable<AdvanceSalaryModel[]> {
    return this.http.get<AdvanceSalaryModel[]>(
      `${this.baseUrl}/user/${userId}/year/${year}`
    );
  }

  // Get advance salaries by user, year, and month
  getAdvanceSalariesByUserYearAndMonth(
    userId: number,
    year: number,
    month: string
  ): Observable<AdvanceSalaryModel[]> {
    return this.http.get<AdvanceSalaryModel[]>(
      `${this.baseUrl}/user/${userId}/year/${year}/month/${month}`
    );
  }

  // Get total advance salary for a user in a specific year
  getTotalAdvanceSalaryByUserAndYear(
    userId: number,
    year: number
  ): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/user/${userId}/total-year/${year}`
    );
  }

  // Get advance salaries within a specific date range
  getAdvanceSalariesByDateRange(
    startDate: Date,
    endDate: Date
  ): Observable<AdvanceSalaryModel[]> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());
    return this.http.get<AdvanceSalaryModel[]>(`${this.baseUrl}/date-range`, {
      params,
    });
  }

  // Get advance salaries for a specific month and year
  getAdvanceSalariesByMonthAndYear(
    month: string,
    year: number
  ): Observable<AdvanceSalaryModel[]> {
    return this.http.get<AdvanceSalaryModel[]>(
      `${this.baseUrl}/month/${month}/year/${year}`
    );
  }

  // Get the latest advance salary record for a user
  getLatestAdvanceSalaryByUser(
    userId: number
  ): Observable<AdvanceSalaryModel[]> {
    return this.http.get<AdvanceSalaryModel[]>(
      `${this.baseUrl}/latest/user/${userId}`
    );
  }
}
