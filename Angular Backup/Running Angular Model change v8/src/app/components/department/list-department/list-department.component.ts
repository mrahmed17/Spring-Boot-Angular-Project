import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../services/department.service';
import { DepartmentModel } from '../../../models/department.model';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-list-department',
  templateUrl: './list-department.component.html',
  styleUrls: ['./list-department.component.css'],
})
export class ListDepartmentComponent implements OnInit {
  departments: DepartmentModel[] = [];
  searchTerm: string = '';

  constructor(
    private departmentService: DepartmentService,
    private router: Router,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (error) => {
        this.handleError('fetching department list', error);
      },
    });
  }

  viewDepartment(departmentId: number): void {
    this.router.navigate(['/department/view', departmentId]);
  }

  editDepartment(departmentId: number): void {
    this.router.navigate(['/department/update', departmentId]);
  }

  deleteDepartment(departmentId: number): void {
    if (confirm('Are you sure you want to delete this department?')) {
      this.departmentService.deleteDepartment(departmentId).subscribe({
        next: () => {
          this.notification.showNotify(
            'Department deleted successfully',
            'success'
          );
          this.loadDepartments();
        },
        error: (error) => {
          this.handleError('deleting department', error);
        },
      });
    }
  }
  searchDepartments(): void {
    if (this.searchTerm.trim()) {
      this.departmentService.findByDepartmentName(this.searchTerm).subscribe({
        next: (departments) => {
          this.departments = departments; 
        },
        error: (error) => {
          this.handleError('searching departments', error);
        },
      });
    } else {
      this.loadDepartments();
    }
  }

  getEmployeesByDepartmentId(departmentId: number): void {
    this.departmentService.getEmployeesByDepartmentId(departmentId).subscribe({
      next: (employees) => {
        console.log(employees);
      },
      error: (error) => {
        this.handleError('fetching employees', error);
      },
    });
  }

  countEmployeesByDepartmentId(departmentId: number): void {
    this.departmentService
      .countEmployeesByDepartmentId(departmentId)
      .subscribe({
        next: (count) => {
          console.log(
            `Number of employees in department ${departmentId}: ${count}`
          );
        },
        error: (error) => {
          this.handleError('counting employees', error);
        },
      });
  }

  private handleError(operation: string, error: any): void {
    console.error(`Error ${operation}:`, error);
    this.notification.showNotify(`Error ${operation}`, 'error');
  }
}
