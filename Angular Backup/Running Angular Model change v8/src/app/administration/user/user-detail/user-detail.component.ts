import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';
import {
  faUser,
  faEnvelope,
  faPhone,
  faIdBadge,
  faCalendarAlt,
  faMoneyCheckDollar,
  faMapMarkedAlt,
  faArrowLeft,
  faUserTag,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
})
export class UserDetailComponent implements OnInit {
  faUser = faUser;
  faArrowLeft = faArrowLeft;
  faEnvelope = faEnvelope;
  faUserTag = faUserTag;
  faPhone = faPhone;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faMoneyCheckDollar = faMoneyCheckDollar;
  faMapMarkedAlt = faMapMarkedAlt;

  user: UserModel | null = null;
  isLoading = false;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    const userId = +this.route.snapshot.paramMap.get('id')!;
    this.isLoading = true;
    this.userService.getUserById(userId).subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (err) => {
        this.notificationService.showNotify(
          'Failed to load user details.',
          'error'
        );
        console.error('Error:', err);
      },
      complete: () => (this.isLoading = false),
    });
  }

  goBack(): void {
    this.router.navigate(['/user/list']);
  }
}