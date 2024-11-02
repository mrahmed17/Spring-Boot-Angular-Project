import { Component, OnInit } from '@angular/core';
import { BranchService } from '../../../services/branch.service';
import { NotificationService } from '../../../services/notification.service';
import { BranchModel } from '../../../models/branch.model';
import { forkJoin, Observable } from 'rxjs';

@Component({
  selector: 'app-report-branch',
  templateUrl: './report-branch.component.html',
  styleUrls: ['./report-branch.component.css'],
})
export class ReportBranchComponent implements OnInit {
  branches: BranchModel[] = [];
  totalBranches: number = 0;
  totalDepartments: number = 0;
  totalEmployees: number = 0;
  companyId: number = 0; // Set this to the actual company ID

  constructor(
    private branchService: BranchService,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadBranches();
    this.countTotalBranches();
    this.countDepartmentsByCompanyId(this.companyId);
    this.countEmployeesByCompanyId(this.companyId);
  }

  loadBranches(): void {
    this.branchService.getAllBranches().subscribe({
      next: (data) => {
        this.branches = data;
      },
      error: (error) => {
        console.error('Error fetching branches', error);
        this.notification.showNotify('Error fetching branches', 'error');
      },
    });
  }

  countTotalBranches(): void {
    this.branchService.getAllBranches().subscribe((data) => {
      this.totalBranches = data.length;
    });
  }

  getEmployeeCount(branchId: number): Observable<number> {
    return this.branchService.countEmployeesByBranchId(branchId);
  }

  getDepartmentCount(branchId: number): Observable<number> {
    return this.branchService.countDepartmentsByBranchId(branchId);
  }

  countDepartmentsByCompanyId(companyId: number): void {
    this.branchService
      .getBranchesByCompanyId(companyId)
      .subscribe((branches) => {
        const departmentCountRequests = branches.map((branch) =>
          this.branchService.countDepartmentsByBranchId(branch.id)
        );

        forkJoin(departmentCountRequests).subscribe((counts) => {
          this.totalDepartments = counts.reduce((acc, count) => acc + count, 0);
        });
      });
  }

  countEmployeesByCompanyId(companyId: number): void {
    this.branchService
      .getBranchesByCompanyId(companyId)
      .subscribe((branches) => {
        const employeeCountRequests = branches.map((branch) =>
          this.branchService.countEmployeesByBranchId(branch.id)
        );

        forkJoin(employeeCountRequests).subscribe((counts) => {
          this.totalEmployees = counts.reduce((acc, count) => acc + count, 0);
        });
      });
  }
}
