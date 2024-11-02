import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { DepartmentModel } from '../models/department.model';
import { UserModel } from '../models/user.model';
import { BranchModel } from '../models/branch.model';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  private baseUrl: string = `${environment.apiUrl}/departments`;

  constructor(private http: HttpClient) {}

  createDepartment(
    department: DepartmentModel,
    departmentPhoto?: File
  ): Observable<DepartmentModel> {
    const formData = this.buildFormData(department, departmentPhoto);
    return this.http
      .post<DepartmentModel>(`${this.baseUrl}/create`, formData)
      .pipe(catchError(this.handleError<DepartmentModel>('createDepartment')));
  }

  updateDepartment(
    id: number,
    department: DepartmentModel,
    departmentPhoto?: File
  ): Observable<DepartmentModel> {
    const formData = this.buildFormData(department, departmentPhoto);
    return this.http
      .put<DepartmentModel>(`${this.baseUrl}/update/${id}`, formData)
      .pipe(catchError(this.handleError<DepartmentModel>('updateDepartment')));
  }

  deleteDepartment(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.baseUrl}/delete/${id}`)
      .pipe(catchError(this.handleError<void>('deleteDepartment')));
  }

  getDepartmentById(id: number): Observable<DepartmentModel> {
    return this.http
      .get<DepartmentModel>(`${this.baseUrl}/find/${id}`)
      .pipe(catchError(this.handleError<DepartmentModel>('getDepartmentById')));
  }

  getAllDepartments(): Observable<DepartmentModel[]> {
    return this.http
      .get<DepartmentModel[]>(`${this.baseUrl}/all`)
      .pipe(
        catchError(this.handleError<DepartmentModel[]>('getAllDepartments', []))
      );
  }

  findByDepartmentName(departmentName: string): Observable<DepartmentModel[]> {
    const params = new HttpParams().set('departmentName', departmentName);
    return this.http
      .get<DepartmentModel[]>(`${this.baseUrl}/search`, { params }) 
      .pipe(
        catchError(
          this.handleError<DepartmentModel[]>('findByDepartmentName', [])
        )
      );
  }

  getBranchesByCompanyId(companyId: number): Observable<BranchModel[]> {
    return this.http
      .get<BranchModel[]>(`${this.baseUrl}/branches/company/${companyId}`)
      .pipe(
        catchError(
          this.handleError<BranchModel[]>('getBranchesByCompanyId', [])
        )
      );
  }

  getEmployeesByDepartmentId(departmentId: number): Observable<UserModel[]> {
    return this.http
      .get<UserModel[]>(`${this.baseUrl}/${departmentId}/employees`)
      .pipe(
        catchError(
          this.handleError<UserModel[]>('getEmployeesByDepartmentId', [])
        )
      );
  }

  countEmployeesByDepartmentId(departmentId: number): Observable<number> {
    return this.http
      .get<number>(`${this.baseUrl}/${departmentId}/employees/count`)
      .pipe(
        catchError(this.handleError<number>('countEmployeesByDepartmentId'))
      );
  }

  getDepartmentsByBranchId(branchId: number): Observable<DepartmentModel[]> {
    return this.http
      .get<DepartmentModel[]>(`${this.baseUrl}/branch/${branchId}`)
      .pipe(
        catchError(
          this.handleError<DepartmentModel[]>('getDepartmentsByBranchId', [])
        )
      );
  }

  getDepartmentsByCompanyId(companyId: number): Observable<DepartmentModel[]> {
    return this.http
      .get<DepartmentModel[]>(`${this.baseUrl}/company/${companyId}`)
      .pipe(
        catchError(
          this.handleError<DepartmentModel[]>('getDepartmentsByCompanyId', [])
        )
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private buildFormData(
    department: DepartmentModel,
    departmentPhoto?: File
  ): FormData {
    const formData = new FormData();
    formData.append(
      'department',
      new Blob([JSON.stringify(department)], { type: 'application/json' })
    );
    if (departmentPhoto) {
      formData.append('departmentPhoto', departmentPhoto);
    }
    return formData;
  }
}
