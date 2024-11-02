import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserModel } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  constructor() { }
  
  // getLoggedInUser(): Observable<UserModel> {
  //   const user: UserModel = {
  //     id: 1,
  //     fullName: 'Raju',
  //     email: 'raju@mail.com',
  //     password:'raju123',
  //   };

  //   return of(user);
  // }
}
