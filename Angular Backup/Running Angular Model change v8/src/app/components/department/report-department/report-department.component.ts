import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../services/department.service';
import { DepartmentModel } from '../../../models/department.model';
import { UserModel } from '../../../models/user.model';
import { NotificationService } from '../../../services/notification.service';
import { BranchModel } from '../../../models/branch.model';

@Component({
  selector: 'app-report-department',
  templateUrl: './report-department.component.html',
  styleUrls: ['./report-department.component.css'],
})
export class ReportDepartmentComponent implements OnInit {
  departments: DepartmentModel[] = [];
  employeesCount: number = 0;
  branches: BranchModel[] = [];
  isLoading: boolean = false;

  constructor(
    private departmentService: DepartmentService,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadBranches();
    this.loadDepartments();
  }

  onBranchChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement; // Cast the event target
    const branchId = selectElement.value; // Get the selected branch ID as a string
    this.getDepartmentsByBranchId(branchId); // Call the method with the selected value
  }

  loadBranches(): void {
    this.isLoading = true;
    this.departmentService.getBranchesByCompanyId(1).subscribe({
      next: (data) => {
        this.branches = data;
        this.isLoading = false; // Reset loading state
      },
      error: (error) => {
        console.error('Error fetching branches', error);
        this.notification.showNotify('Error fetching branches', 'error');
        this.isLoading = false; // Reset loading state
      },
    });
  }

  loadDepartments(): void {
    this.isLoading = true; // Set loading state
    this.departmentService.getDepartmentsByCompanyId(1).subscribe({
      next: (data) => {
        this.departments = data;
        this.isLoading = false; // Reset loading state
      },
      error: (error) => {
        console.error('Error fetching departments', error);
        this.notification.showNotify('Error fetching departments', 'error');
        this.isLoading = false; // Reset loading state
      },
    });
  }

  getEmployeesByDepartmentId(departmentId: number): void {
    this.departmentService.getEmployeesByDepartmentId(departmentId).subscribe({
      next: (employees: UserModel[]) => {
        console.log('Employees:', employees);
      },
      error: (error) => {
        console.error('Error fetching employees', error);
        this.notification.showNotify('Error fetching employees', 'error');
      },
    });
  }

  countEmployeesByDepartmentId(departmentId: number): void {
    this.departmentService
      .countEmployeesByDepartmentId(departmentId)
      .subscribe({
        next: (count) => {
          this.employeesCount = count;
        },
        error: (error) => {
          console.error('Error counting employees', error);
          this.notification.showNotify('Error counting employees', 'error');
        },
      });
  }

  getDepartmentsByBranchId(branchId: string): void {
    const id = parseInt(branchId, 10);
    this.departmentService.getDepartmentsByBranchId(id).subscribe({
      next: (departments) => {
        this.departments = departments;
      },
      error: (err) => {
        console.error('Error fetching departments:', err);
      },
    });
  }

  getDepartmentsByCompanyId(companyId: number): void {
    this.departmentService.getDepartmentsByCompanyId(companyId).subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (error) => {
        console.error('Error fetching departments by company', error);
        this.notification.showNotify(
          'Error fetching departments by company',
          'error'
        );
      },
    });
  }
}
