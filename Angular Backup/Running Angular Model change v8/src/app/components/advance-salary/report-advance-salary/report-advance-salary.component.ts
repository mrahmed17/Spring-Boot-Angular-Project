import { Component, OnInit } from '@angular/core';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { UserService } from '../../../services/user.service';
import { UserModel } from '../../../models/user.model';
import * as FileSaver from 'file-saver';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-report-advance-salary',
  templateUrl: './report-advance-salary.component.html',
  styleUrls: ['./report-advance-salary.component.css'],
})
export class ReportAdvanceSalaryComponent implements OnInit {
  advanceSalaries: AdvanceSalaryModel[] = [];
  filteredAdvanceSalaries: AdvanceSalaryModel[] = [];
  users: UserModel[] = [];
  selectedUserId: number | null = null;
  selectedDateRange: { startDate: Date | null; endDate: Date | null } = {
    startDate: null,
    endDate: null,
  };
  totalAdvanceSalary: number = 0;
  searchName: string = '';
  errorMessage: string | null = null;

  constructor(
    private advanceSalaryService: AdvanceSalaryService,
    private userService: UserService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadAdvanceSalaries();
    this.loadUsers();
  }

  loadAdvanceSalaries() {
    this.advanceSalaryService.getAllAdvanceSalaries().subscribe({
      next: (salaries: AdvanceSalaryModel[]) => {
        this.advanceSalaries = salaries;
        this.filteredAdvanceSalaries = [...this.advanceSalaries]; // Initial filter state
        this.calculateTotalAdvanceSalary();
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
      this.filteredAdvanceSalaries = this.advanceSalaries.filter(
        (salary) => salary.user.id === this.selectedUserId
      );
    } else {
      this.filteredAdvanceSalaries = [...this.advanceSalaries];
    }
    this.calculateTotalAdvanceSalary();
  }

  onFilterByDateRange() {
    const { startDate, endDate } = this.selectedDateRange;
    if (startDate && endDate) {
      this.filteredAdvanceSalaries = this.advanceSalaries.filter(
        (salary) =>
          new Date(salary.advanceDate) >= startDate &&
          new Date(salary.advanceDate) <= endDate
      );
    } else {
      this.filteredAdvanceSalaries = [...this.advanceSalaries];
    }
    this.calculateTotalAdvanceSalary();
  }

  onSearchByName() {
    if (this.searchName.trim()) {
      this.filteredAdvanceSalaries = this.advanceSalaries.filter((salary) =>
        salary.user.fullName
          .toLowerCase()
          .includes(this.searchName.toLowerCase())
      );
    } else {
      this.filteredAdvanceSalaries = [...this.advanceSalaries];
    }
    this.calculateTotalAdvanceSalary();
  }

  calculateTotalAdvanceSalary() {
    this.totalAdvanceSalary = this.filteredAdvanceSalaries.reduce(
      (total, salary) => total + salary.advanceSalary,
      0
    );
  }

  exportToCSV() {
    const csvData = this.filteredAdvanceSalaries.map((salary) => ({
      User: salary.user.fullName,
      'Advance Salary': salary.advanceSalary,
      Reason: salary.reason,
      'Advance Date': salary.advanceDate,
    }));

    const csvContent = [
      ['User', 'Advance Salary', 'Reason', 'Advance Date'],
      ...csvData.map((row) => Object.values(row)),
    ]
      .map((e) => e.join(','))
      .join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    FileSaver.saveAs(blob, 'advance-salary-report.csv');
    this.notificationService.showNotify(
      'Report exported successfully!',
      'success'
    );
  }
}
