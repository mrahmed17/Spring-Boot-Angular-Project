import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { UserModel } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl: string = `${environment.apiUrl}/users`;

  // private baseUrl: string = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) {}

  createUser(formData: FormData): Observable<string> {
    return this.httpClient
      .post<string>(`${this.baseUrl}/create`, formData, {
        responseType: 'text' as 'json',
      })
      .pipe(catchError(this.handleError<string>('createUser')));
  }

  updateUser(
    id: number,
    user: UserModel,
    profilePhoto?: File
  ): Observable<any> {
    const formData = new FormData();
    formData.append('user', JSON.stringify(user));
    if (profilePhoto) {
      formData.append('profilePhoto', profilePhoto);
    }

    return this.httpClient
      .put(`${this.baseUrl}/update/${id}`, formData)
      .pipe(catchError(this.handleError<any>('updateUser')));
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(this.baseUrl + '/')
      .pipe(catchError(this.handleError<UserModel[]>('getAllUsers', [])));
  }

  getUserById(id: number): Observable<UserModel> {
    return this.httpClient
      .get<UserModel>(`${this.baseUrl}/find/${id}`)
      .pipe(catchError(this.handleError<UserModel>('getUserById')));
  }

  deleteUserById(id: number): Observable<void> {
    return this.httpClient
      .delete<void>(`${this.baseUrl}/delete/${id}`)
      .pipe(catchError(this.handleError<void>('deleteUserById')));
  }

  getUserByEmail(email: string): Observable<UserModel> {
    return this.httpClient
      .get<UserModel>(`${this.baseUrl}/email/${email}`)
      .pipe(catchError(this.handleError<UserModel>('getUserByEmail')));
  }

  getUsersWithSalaryGreaterThanOrEqual(
    salary: number
  ): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/salary/greaterThanOrEqual/${salary}`)
      .pipe(
        catchError(
          this.handleError<UserModel[]>(
            'getUsersWithSalaryGreaterThanOrEqual',
            []
          )
        )
      );
  }

  getUsersWithSalaryLessThanOrEqual(salary: number): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/salary/lessThanOrEqual/${salary}`)
      .pipe(
        catchError(
          this.handleError<UserModel[]>('getUsersWithSalaryLessThanOrEqual', [])
        )
      );
  }

  getUsersByRole(role: string): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/role/${role}`)
      .pipe(catchError(this.handleError<UserModel[]>('getUsersByRole', [])));
  }

  getUsersByFullNamePart(name: string): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/search/name/${name}`)
      .pipe(
        catchError(this.handleError<UserModel[]>('getUsersByFullNamePart', []))
      );
  }

  getUsersByGender(gender: string): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/gender/${gender}`)
      .pipe(catchError(this.handleError<UserModel[]>('getUsersByGender', [])));
  }

  getUsersByJoinedDate(joinedDate: string): Observable<UserModel[]> {
    return this.httpClient
      .get<UserModel[]>(`${this.baseUrl}/joinedDate/${joinedDate}`)
      .pipe(
        catchError(this.handleError<UserModel[]>('getUsersByJoinedDate', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
