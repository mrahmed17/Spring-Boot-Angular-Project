import { Component, OnInit } from '@angular/core';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { UserService } from '../../../services/user.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-advance-salary',
  templateUrl: './list-advance-salary.component.html',
  styleUrls: ['./list-advance-salary.component.css'],
})
export class ListAdvanceSalaryComponent implements OnInit {
  advanceSalaries: AdvanceSalaryModel[] = [];
  filteredAdvanceSalaries: AdvanceSalaryModel[] = [];
  users: UserModel[] = [];
  selectedUserId: number | null = null;
  selectedDateRange: { startDate: Date | null; endDate: Date | null } = {
    startDate: null,
    endDate: null,
  };
  totalAdvanceSalary: number = 0;
  page: number = 1;
  pageSize: number = 10;
  searchName: string = '';
  errorMessage: string | null = null;

  constructor(
    private advanceSalaryService: AdvanceSalaryService,
    private userService: UserService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAdvanceSalaries();
    this.loadUsers();
  }

  loadAdvanceSalaries() {
    this.advanceSalaryService.getAllAdvanceSalaries().subscribe({
      next: (salaries: AdvanceSalaryModel[]) => {
        this.advanceSalaries = salaries;
        this.filteredAdvanceSalaries = this.paginateSalaries();
      },
      error: (err) => {
        this.errorMessage = 'Failed to load advance salaries.';
      },
    });
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users: UserModel[]) => {
        this.users = users;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load users.';
      },
    });
  }

  onFilterByUser() {
    if (this.selectedUserId) {
      this.advanceSalaryService
        .getAdvanceSalariesByUser(this.selectedUserId)
        .subscribe({
          next: (salaries) => {
            this.advanceSalaries = salaries;
            this.filteredAdvanceSalaries = this.paginateSalaries();
            this.calculateTotalAdvanceSalary();
          },
          error: (err) => {
            this.errorMessage = 'Error filtering advance salaries by user.';
          },
        });
    } else {
      this.loadAdvanceSalaries();
    }
  }

  onFilterByDateRange() {
    const { startDate, endDate } = this.selectedDateRange;
    if (startDate && endDate) {
      this.advanceSalaryService
        .getAdvanceSalariesByDateRange(startDate, endDate)
        .subscribe({
          next: (salaries) => {
            this.advanceSalaries = salaries;
            this.filteredAdvanceSalaries = this.paginateSalaries();
            this.calculateTotalAdvanceSalary();
          },
          error: (err) => {
            this.errorMessage =
              'Error filtering advance salaries by date range.';
          },
        });
    } else {
      this.loadAdvanceSalaries();
    }
  }

  onSearchByName() {
    if (this.searchName.trim()) {
      this.advanceSalaryService
        .getAdvanceSalariesByName(this.searchName)
        .subscribe({
          next: (salaries) => {
            this.advanceSalaries = salaries;
            this.filteredAdvanceSalaries = this.paginateSalaries();
          },
          error: (err) => {
            this.errorMessage = 'Error searching advance salaries by name.';
          },
        });
    } else {
      this.loadAdvanceSalaries();
    }
  }

  paginateSalaries(): AdvanceSalaryModel[] {
    const start = (this.page - 1) * this.pageSize;
    return this.advanceSalaries.slice(start, start + this.pageSize);
  }

  getTotalPages(): number {
    return Math.ceil(this.advanceSalaries.length / this.pageSize);
  }

  calculateTotalAdvanceSalary() {
    this.totalAdvanceSalary = this.advanceSalaries.reduce(
      (total, salary) => total + salary.advanceSalary,
      0
    );
  }

  onPageChange(page: number) {
    this.page = page;
    this.filteredAdvanceSalaries = this.paginateSalaries();
  }

  viewAdvanceSalary(id: number) {
    this.router.navigate(['/advance-salary/view', id]);
  }

  editAdvanceSalary(id: number) {
    this.router.navigate(['/advance-salary/edit', id]);
  }

  deleteAdvanceSalary(id: number) {
    if (confirm('Are you sure you want to delete this advance salary?')) {
      this.advanceSalaryService.deleteAdvanceSalary(id).subscribe({
        next: () => {
          this.notificationService.showNotify(
            'Advance Salary deleted successfully!',
            'success'
          );
          this.loadAdvanceSalaries();
        },
        error: (err) => {
          this.notificationService.showNotify(
            'Error deleting advance salary.',
            'error'
          );
        },
      });
    }
  }
}
