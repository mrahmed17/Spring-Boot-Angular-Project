import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../services/notification.service';
import {
  faCheckCircle,
  faExclamationCircle,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css'],
})
export class NotificationComponent implements OnInit {
  faCheckCircle = faCheckCircle;
  faExclamationCircle = faExclamationCircle;

  message: string = '';
  type: string = '';

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.notificationService.notification$.subscribe(({ message, type }) => {
      this.message = message;
      this.type = type;
      setTimeout(() => {
        this.message = '';
        this.type = '';
      }, 3000);
    });
  }
}
