import { Component, OnInit } from '@angular/core';
import { AttendanceModel } from '../../../models/attendance.model';
import { AttendanceService } from '../../../services/attendance.service';
import { FormControl } from '@angular/forms';
import { UserModel } from '../../../models/user.model';
import { NotificationService } from '../../../services/notification.service';
import { debounceTime } from 'rxjs/operators';

import {
  faCalendarAlt,
  faClock,
  faIdBadge,
  faListUl,
  faSearch,
  faUser,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-list-attendance',
  templateUrl: './list-attendance.component.html',
  styleUrls: ['./list-attendance.component.css'],
})
export class ListAttendanceComponent implements OnInit {
  faListUl = faListUl;
  faIdBadge = faIdBadge;
  faCalendarAlt = faCalendarAlt;
  faClock = faClock;
  faUser = faUser;
  faSearch = faSearch;

  attendances: AttendanceModel[] = [];
  filteredAttendances: AttendanceModel[] = [];
  users: UserModel[] = [];
  selectedUserId: number | null = null;
  searchTerm: string = '';
  searchControl = new FormControl('');
  selectedDate: Date = new Date();
  page: number = 0;
  size: number = 10;

  constructor(
    private attendanceService: AttendanceService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.getAllAttendances();
    this.loadUsers();

    this.searchControl.valueChanges
      .pipe(debounceTime(300)) 
      .subscribe((searchTerm) => {
        this.searchTerm = searchTerm ?? '';
        this.applyFilters();
      });
  }

  loadUsers(): void {
    this.attendanceService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Error loading users:', error);
        this.notificationService.showNotify('Error loading users.', 'error');
      },
    });
  }

  getAllAttendances(): void {
    this.attendanceService.getAllAttendances().subscribe({
      next: (data) => {
        this.attendances = data;
        this.applyFilters(); 
      },
      error: (error) => {
        console.error('Error fetching attendances', error);
      },
    });
  }

  applyFilters(): void {
    let filtered = this.attendances;

    if (this.selectedUserId) {
      filtered = filtered.filter((att) => att.user.id === this.selectedUserId);
    }

    if (this.searchTerm) {
      filtered = filtered.filter((att) =>
        att.user.fullName.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    const startIndex = this.page * this.size;
    this.filteredAttendances = filtered.slice(
      startIndex,
      startIndex + this.size
    );
  }

  onUserChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.selectedUserId = Number(target.value) || null;
    this.applyFilters();
  }

  onDateChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.selectedDate = new Date(target.value);
    this.getAllAttendances();
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.applyFilters(); 
    }
  }

  nextPage(): void {
    this.page++;
    this.applyFilters(); 
  }

  isPreviousDisabled(): boolean {
    return this.page <= 0;
  }

  isNextDisabled(): boolean {
    return (this.page + 1) * this.size >= this.attendances.length;
  }
}
