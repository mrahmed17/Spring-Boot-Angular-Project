import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.baseUrl}/`);
  }

  getUserById(id: number): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.baseUrl}/view/${id}`);
  }

  saveUser(user: UserModel): Observable<UserModel> {
    return this.http.post<UserModel>(`${this.baseUrl}/save`, user);
  }

  updateUser(id: number, user: UserModel): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/update/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  // getCurrentUser(): Observable<UserModel> {
  //   const token = localStorage.getItem('token');
  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  //   return this.http.get<UserModel>(`${this.baseUrl}/current`, { headers });
  // }
}