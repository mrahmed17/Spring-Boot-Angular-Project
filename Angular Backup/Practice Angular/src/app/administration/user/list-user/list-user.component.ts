import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { faCog, faEdit, faEnvelope, faEye, faIdBadge, faMoneyCheckDollar, faPhone, faTrash, faUser, faUserPlus, faUsers, faUserTag } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';
import { RoleEnum } from '../../../models/role.enum';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css'],
})
export class ListUserComponent implements OnInit {
  faUsers = faUsers;
  faUser = faUser;
  faIdBadge = faIdBadge;
  faEnvelope = faEnvelope;
  faPhone = faPhone;
  faUserTag = faUserTag;
  faMoneyCheckDollar = faMoneyCheckDollar;
  faCog = faCog;
  faEye = faEye;
  faTrash = faTrash;
  faEdit = faEdit;
  faUserPlus = faUserPlus;

  users: UserModel[] = [];
  roles = Object.values(RoleEnum);
  filters = { role: '', email: '', salary: null as number | null };
  isLoading = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    // this.isLoading = true;
    // this.userService.getAllUsers(this.filters).subscribe({
    //   next: (data) => {
    //     this.users = data;
    //   },
    //   error: (err) => {
    //     this.notificationService.showNotify('Failed to load users.', 'error');
    //     console.error('Error:', err);
    //   },
    //   complete: () => (this.isLoading = false),
    // });
  }

  applyFilters(): void {
    this.loadUsers();
  }

  viewUser(userId: number): void {
    this.router.navigate(['/user/view', userId]);
  }

  // Edit a user by ID
  editUser(userId: number): void {
    this.router.navigate(['/user/edit', userId]);
  }

  // Delete a user by ID
  deleteUser(userId: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.isLoading = true;
      this.userService.deleteUser(userId).subscribe({
        next: () => {
          this.notificationService.showNotify(
            'User deleted successfully.',
            'success'
          );
          this.loadUsers(); // Reload users after deletion
        },
        error: (err) => {
          this.notificationService.showNotify(
            'Failed to delete user.',
            'error'
          );
          console.error('Error:', err);
        },
        complete: () => (this.isLoading = false),
      });
    }
  }
}