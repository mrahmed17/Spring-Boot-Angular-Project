import { Component, OnInit } from '@angular/core';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { CompanyService } from '../../../services/company.service';
import { NotificationService } from '../../../services/notification.service';
import { CompanyModel } from '../../../models/company.model';
import { UserModel } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-report-company',
  templateUrl: './report-company.component.html',
  styleUrls: ['./report-company.component.css'],
})
export class ReportCompanyComponent implements OnInit {
  advanceSalary: AdvanceSalaryModel = new AdvanceSalaryModel();
  users: UserModel[] = [];
  selectedCompanyId: number | null = null;
  selectedBranchId!: number;
  employeeCountInCompany!: number;
  employeeCountInBranch!: number;
  branches: any[] = [];
  departments: any[] = [];
  employees: any[] = [];
  companies: CompanyModel[] = [];

  constructor(
    private companyService: CompanyService,
    private notification: NotificationService,
    private userService: UserService,
    private router: Router,
    private advanceSalaryService: AdvanceSalaryService
  ) {}

  ngOnInit(): void {
    this.loadCompanies();
    this.loadUsers();
  }

  loadCompanies() {
    this.companyService.getAllCompanies().subscribe({
      next: (companies) => {
        this.companies = companies;
      },
      error: (error) => {
        console.error('Error fetching companies', error);
        this.notification.showNotify('Error fetching companies', 'error');
      },
    });
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users: UserModel[]) => {
        this.users = users;
      },
      error: (err) => {
        this.notification.showNotify('Failed to load users.', 'error');
      },
    });
  }

  onSubmit(form: NgForm) {
    if (form.invalid) {
      return; 
    }

    this.advanceSalaryService
      .createAdvanceSalary(this.advanceSalary)
      .subscribe({
        next: (response) => {
          this.notification.showNotify(
            'Advance Salary Created successfully!',
            'success'
          );
          // Optionally navigate to another route
        },
        error: (err) => {
          this.notification.showNotify(
            'Error creating advance salary!',
            'error'
          );
        },
      });
  }

  cancel(): void {
    this.router.navigate(['/company/list']);
  }
}
