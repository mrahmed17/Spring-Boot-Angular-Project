import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl: string = environment.apiUrl + '/user';

  // private baseUrl = 'http://localhost:8080/api/user';

  constructor(private httpClient: HttpClient) {}


  createUser(userModel: UserModel, image: File): Observable<any> {
    const formData = new FormData(); 

    formData.append(
      'userModel',
      new Blob([JSON.stringify(userModel)], { type: 'application/json' })
    );
    formData.append('image', image);

    return this.httpClient.post(this.baseUrl + '/save', formData);
  }

  getAllUsers(): Observable<any> {
    return this.httpClient
      .get<any>(`${this.baseUrl}`)
      .pipe(catchError(this.handleError));
  }

  getUserById(id: number): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/view/${id}`)
      .pipe(catchError(this.handleError));
  }

  deleteUser(id: number): Observable<any> {
    return this.httpClient
      .delete(`${this.baseUrl}/delete/${id}`)
      .pipe(catchError(this.handleError));
  }

  updateUser(id: string, userModel: UserModel, image?: File): Observable<any> {
    return this.httpClient
      .put(`${this.baseUrl}/update/${id}`, userModel)
      .pipe(catchError(this.handleError));
  }

  getUserByEmail(email: string): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/findByEmail`, {
        params: new HttpParams().set('email', email),
      })
      .pipe(catchError(this.handleError));
  }

  getUsersWithSalaryGreaterThan(salary: number): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/findBySalaryGreaterThanOrEqual`, {
        params: new HttpParams().set('salary', salary),
      })
      .pipe(catchError(this.handleError));
  }

  getUsersWithSalaryLessThan(salary: number): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/findBySalaryLessThanOrEqual`, {
        params: new HttpParams().set('salary', salary),
      })
      .pipe(catchError(this.handleError));
  }

  getUsersByRole(role: string): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/findByRole`, {
        params: new HttpParams().set('role', role),
      })
      .pipe(catchError(this.handleError));
  }

  findUserByName(name: string): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/searchByName`, {
        params: new HttpParams().set('name', name),
      })
      .pipe(catchError(this.handleError));
  }

  findUserByGender(gender: string): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/searchByGender`, {
        params: new HttpParams().set('gender', gender),
      })
      .pipe(catchError(this.handleError));
  }

  findUserByJoinedDate(joinedDate: string): Observable<any> {
    return this.httpClient
      .get(`${this.baseUrl}/searchByJoinedDate`, {
        params: new HttpParams().set('joinedDate', joinedDate),
      })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => error.message || error);
  }
}
