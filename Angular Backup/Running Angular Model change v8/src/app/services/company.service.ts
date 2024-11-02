import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { CompanyModel } from '../models/company.model';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  private baseUrl: string = `${environment.apiUrl}/companies`;

  constructor(private http: HttpClient) {}

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
     
      errorMessage = `Error: ${error.error.message}`;
    } else {
     
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  createCompany(company: CompanyModel, companyPhoto?: File): Observable<any> {
    const formData = new FormData();
    formData.append(
      'company',
      new Blob([JSON.stringify(company)], { type: 'application/json' })
    );

    if (companyPhoto) {
      formData.append('companyPhoto', companyPhoto);
    }

    return this.http
      .post(`${this.baseUrl}/create`, formData, {
        headers: new HttpHeaders(),
        observe: 'response',
        responseType: 'json',
      })
      .pipe(
        map((response: any) => response.body),
        catchError(this.handleError)
      );
  }

  getAllCompanies(): Observable<CompanyModel[]> {
    return this.http
      .get<CompanyModel[]>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  getCompanyById(id: number): Observable<CompanyModel> {
    return this.http
      .get<CompanyModel>(`${this.baseUrl}/find/${id}`)
      .pipe(catchError(this.handleError));
  }

  updateCompany(
    id: number,
    company: CompanyModel,
    companyPhoto?: File
  ): Observable<any> {
    const formData = new FormData();
    formData.append(
      'company',
      new Blob([JSON.stringify(company)], { type: 'application/json' })
    );

    if (companyPhoto) {
      formData.append('companyPhoto', companyPhoto);
    }

    return this.http
      .put(`${this.baseUrl}/update/${id}`, formData, {
        headers: new HttpHeaders(),
        responseType: 'text',
      })
      .pipe(catchError(this.handleError));
  }

  deleteCompany(id: number): Observable<any> {
    return this.http
      .delete(`${this.baseUrl}/delete/${id}`, {
        responseType: 'json', 
      })
      .pipe(catchError(this.handleError));
  }

  findByCompanyName(companyName: string): Observable<CompanyModel> {
    return this.http
      .get<CompanyModel>(`${this.baseUrl}/search?companyName=${companyName}`)
      .pipe(catchError(this.handleError));
  }

  getBranchesByCompanyId(companyId: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.baseUrl}/${companyId}/branches`)
      .pipe(catchError(this.handleError));
  }

  getDepartmentsByBranchId(branchId: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.baseUrl}/branches/${branchId}/departments`)
      .pipe(catchError(this.handleError));
  }

  getDepartmentsByCompanyId(companyId: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.baseUrl}/${companyId}/departments`)
      .pipe(catchError(this.handleError));
  }

  getEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.baseUrl}/departments/${departmentId}/employees`)
      .pipe(catchError(this.handleError));
  }

  countEmployeesByCompanyId(companyId: number): Observable<number> {
    return this.http
      .get<number>(`${this.baseUrl}/${companyId}/employee-count`)
      .pipe(catchError(this.handleError));
  }

  countEmployeesByBranchId(branchId: number): Observable<number> {
    return this.http
      .get<number>(`${this.baseUrl}/branches/${branchId}/employee-count`)
      .pipe(catchError(this.handleError));
  }
}
