import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { LeaveService } from '../../services/leave.service';
import { SalaryService } from '../../services/salary.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  totalUsers!: number;
  totalLeaves!: number;
  totalSalary!: number;
  totalPendingLeaves!: number;

  constructor(
    private userService: UserService,
    private leaveService: LeaveService,
    private salaryService: SalaryService
  ) {}

  ngOnInit(): void {
    this.loadUserMetrics();
    this.loadLeaveMetrics();
    this.loadSalaryMetrics();
  }

  loadUserMetrics(): void {
    this.userService.getAllUsers().subscribe((users) => {
      this.totalUsers = users.length;
    });
  }

  loadLeaveMetrics(): void {
    this.leaveService.getPendingLeaveRequests().subscribe((leaves) => {
      this.totalPendingLeaves = leaves.length;
    });

    // You can call more methods like getLeavesByUserAndYear or other custom methods
  }

  loadSalaryMetrics(): void {
    const userId = 1; // Use logged-in user's ID
    const currentYear = new Date().getFullYear();

    this.salaryService
      .getTotalSalaryByUserAndYear(userId, currentYear)
      .subscribe((totalSalary) => {
        this.totalSalary = totalSalary;
      });
  }
}
