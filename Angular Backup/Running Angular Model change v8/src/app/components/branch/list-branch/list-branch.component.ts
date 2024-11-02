import { Component, OnInit } from '@angular/core';
import { BranchService } from '../../../services/branch.service';
import { BranchModel } from '../../../models/branch.model';
import { DepartmentModel } from '../../../models/department.model';
import { UserModel } from '../../../models/user.model';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-list-branch',
  templateUrl: './list-branch.component.html',
  styleUrls: ['./list-branch.component.css'],
})
export class ListBranchComponent implements OnInit {
  branches: BranchModel[] = [];
  departments: DepartmentModel[] = [];
  employees: UserModel[] = [];
  searchTerm: string = '';

  constructor(
    private branchService: BranchService,
    private router: Router,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadBranches();
  }

  loadBranches(): void {
    this.branchService.getAllBranches().subscribe({
      next: (data) => {
        this.branches = data;
      },
      error: (error) => {
        console.error('Error fetching branch list', error);
        this.notification.showNotify('Error fetching branch list', 'error');
      },
    });
  }

  viewBranch(branchId: number): void {
    this.router.navigate(['/branch/view', branchId]);
  }

  editBranch(branchId: number): void {
    this.router.navigate(['/branch/update', branchId]);
  }

  deleteBranch(branchId: number): void {
    if (confirm('Are you sure you want to delete this branch?')) {
      this.branchService.deleteBranch(branchId).subscribe({
        next: () => {
          this.notification.showNotify(
            'Branch deleted successfully',
            'success'
          );
          this.loadBranches();
        },
        error: (error) => {
          console.error('Error deleting branch', error);
          this.notification.showNotify('Error deleting branch', 'error');
        },
      });
    }
  }

  searchBranches(): void {
    if (this.searchTerm) {
      this.branchService.findByBranchName(this.searchTerm).subscribe({
        next: (branch) => {
          this.branches = [branch];
        },
        error: (error) => {
          console.error('Error searching branches', error);
          this.notification.showNotify('Error searching branches', 'error');
        },
      });
    } else {
      this.loadBranches();
    }
  }

  getDepartmentsByBranch(branchId: number): void {
    this.branchService.getDepartmentsByBranchId(branchId).subscribe({
      next: (departments) => {
        this.departments = departments;
      },
      error: (error) => {
        console.error('Error fetching departments', error);
        this.notification.showNotify('Error fetching departments', 'error');
      },
    });
  }

  getEmployeesByBranch(branchId: number): void {
    this.branchService.getEmployeesByBranchId(branchId).subscribe({
      next: (employees) => {
        this.employees = employees;
      },
      error: (error) => {
        console.error('Error fetching employees', error);
        this.notification.showNotify('Error fetching employees', 'error');
      },
    });
  }
}
