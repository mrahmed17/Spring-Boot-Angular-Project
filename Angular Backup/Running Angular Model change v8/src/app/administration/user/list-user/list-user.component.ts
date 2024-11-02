import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import {
  faCog,
  faEdit,
  faEnvelope,
  faEye,
  faIdBadge,
  faMoneyCheckDollar,
  faPhone,
  faTrash,
  faUser,
  faUserPlus,
  faUsers,
  faUserTag,
} from '@fortawesome/free-solid-svg-icons';
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
  faCog = faCog;
  faEye = faEye;
  faTrash = faTrash;
  faEdit = faEdit;
  faUserPlus = faUserPlus;

  users: UserModel[] = [];
  filteredUsers: UserModel[] = [];
  searchQuery: string = '';
  selectedRole: RoleEnum | null = null;
  minSalary: number | null = null;
  maxSalary: number | null = null;
  genderFilter: string = '';
  errorMessage: string | null = null;

  roleOptions: string[] = Object.values(RoleEnum).filter(
    (value) => typeof value === 'string'
  ) as string[];

  constructor(
    private userService: UserService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.filteredUsers = users;
      },
      error: (err) => {
        this.errorMessage = 'An error occurred while loading users.';
        console.error(err);
      },
    });
  }

  searchUsers(): void {
    this.filteredUsers = this.users.filter(
      (user) =>
        user.fullName?.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        user.email?.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
    this.applyFilters();
  }

  filterByRole(): void {
    this.filteredUsers = this.users.filter(
      (user) =>
        this.selectedRole === null ||
        (user.role &&
          Array.isArray(user.role) &&
          user.role.includes(this.selectedRole))
    );
  }

  filterBySalary(): void {
    this.filteredUsers = this.users.filter(
      (user) =>
        (this.minSalary === null ||
          (user.basicSalary && user.basicSalary >= this.minSalary)) &&
        (this.maxSalary === null ||
          (user.basicSalary && user.basicSalary <= this.maxSalary))
    );
  }

  filterByGender(): void {
    this.filteredUsers = this.users.filter(
      (user) => this.genderFilter === '' || user.gender === this.genderFilter
    );
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedRole = null;
    this.minSalary = null;
    this.maxSalary = null;
    this.genderFilter = '';
    this.filteredUsers = this.users;
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUserById(id).subscribe({
        next: () => {
          this.notificationService.showNotify(
            'User deleted successfully',
            'success'
          );
          this.loadUsers();
        },
        error: (err) => {
          this.errorMessage = 'An error occurred while deleting the user.';
          console.error('Delete Error:', err);
        },
      });
    }
  }

  viewUser(id: number | undefined): void {
    if (id != null) {
      this.router.navigate([`/user/view/${id}`]);
    }
  }

  editUser(id: number | undefined): void {
    if (id != null) {
      this.router.navigate([`/user/edit/${id}`]);
    }
  }

  applyFilters(): void {
    this.filteredUsers = this.users;
    this.filterByRole();
    this.filterBySalary();
    this.filterByGender();
  }
}
