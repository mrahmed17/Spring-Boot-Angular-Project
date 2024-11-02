import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private notificationSubject = new Subject<{
    message: string;
    type: string;
  }>();
  notification$ = this.notificationSubject.asObservable();

  showNotify(message: string, type: string) :void {
    this.notificationSubject.next({ message, type });
     alert(`${type.toUpperCase()}: ${message}`);
  }
  // private notificationSubject = new Subject<string>();
  // notification$ = this.notificationSubject.asObservable();

  // showNotify(message: string, p0: string) {
  //   this.notificationSubject.next(message);
  // }
}
