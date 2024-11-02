import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { BranchModel } from '../models/branch.model';
import { DepartmentModel } from '../models/department.model';
import { UserModel } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class BranchService {
  private baseUrl: string = `${environment.apiUrl}/branches`;

  constructor(private http: HttpClient) {}

  createBranch(branch: BranchModel, branchPhoto?: File): Observable<string> {
    const formData = new FormData();
    formData.append(
      'branch',
      new Blob([JSON.stringify(branch)], { type: 'application/json' })
    );
    if (branchPhoto) {
      formData.append('branchPhoto', branchPhoto);
    }

    return this.http
      .post<string>(`${this.baseUrl}/create`, formData)
      .pipe(catchError(this.handleError<string>('createBranch')));
  }

  updateBranch(
    id: number,
    branch: BranchModel,
    branchPhoto?: File
  ): Observable<BranchModel> {
    const formData = new FormData();
    formData.append(
      'branch',
      new Blob([JSON.stringify(branch)], { type: 'application/json' })
    );
    if (branchPhoto) {
      formData.append('branchPhoto', branchPhoto);
    }

    return this.http
      .put<BranchModel>(`${this.baseUrl}/update/${id}`, formData)
      .pipe(catchError(this.handleError<BranchModel>('updateBranch')));
  }

  deleteBranch(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.baseUrl}/delete/${id}`)
      .pipe(catchError(this.handleError<void>('deleteBranch')));
  }

  getBranchById(id: number): Observable<BranchModel> {
    return this.http
      .get<BranchModel>(`${this.baseUrl}/find/${id}`)
      .pipe(catchError(this.handleError<BranchModel>('getBranchById')));
  }

  getAllBranches(): Observable<BranchModel[]> {
    return this.http
      .get<BranchModel[]>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError<BranchModel[]>('getAllBranches', [])));
  }

  findByBranchName(branchName: string): Observable<BranchModel> {
    const params = new HttpParams().set('branchName', branchName);
    return this.http
      .get<BranchModel>(`${this.baseUrl}/search`, { params })
      .pipe(catchError(this.handleError<BranchModel>('findByBranchName')));
  }

  getDepartmentsByBranchId(branchId: number): Observable<DepartmentModel[]> {
    return this.http
      .get<DepartmentModel[]>(`${this.baseUrl}/${branchId}/departments`)
      .pipe(
        catchError(
          this.handleError<DepartmentModel[]>('getDepartmentsByBranchId', [])
        )
      );
  }

  getEmployeesByBranchId(branchId: number): Observable<UserModel[]> {
    return this.http
      .get<UserModel[]>(`${this.baseUrl}/${branchId}/employees`)
      .pipe(
        catchError(this.handleError<UserModel[]>('getEmployeesByBranchId', []))
      );
  }

  countDepartmentsByBranchId(branchId: number): Observable<number> {
    return this.http
      .get<number>(`${this.baseUrl}/${branchId}/department-count`)
      .pipe(catchError(this.handleError<number>('countDepartmentsByBranchId')));
  }

  countEmployeesByBranchId(branchId: number): Observable<number> {
    return this.http
      .get<number>(`${this.baseUrl}/${branchId}/employee-count`)
      .pipe(catchError(this.handleError<number>('countEmployeesByBranchId')));
  }

  getBranchesByCompanyId(companyId: number): Observable<BranchModel[]> {
    return this.http
      .get<BranchModel[]>(`${this.baseUrl}/company/${companyId}`)
      .pipe(
        catchError(
          this.handleError<BranchModel[]>('getBranchesByCompanyId', [])
        )
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
