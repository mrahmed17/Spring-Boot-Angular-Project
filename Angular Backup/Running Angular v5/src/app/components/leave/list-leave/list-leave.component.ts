import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { UserModel } from '../../../models/user.model';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';
import { RequestStatusEnum } from '../../../models/request-status.enum';

@Component({
  selector: 'app-list-leave',
  templateUrl: './list-leave.component.html',
  styleUrls: ['./list-leave.component.css'],
})
export class ListLeaveComponent implements OnInit {
  leaves: LeaveModel[] = [];
  filteredLeaves: LeaveModel[] = [];
  filterUserId: number | undefined;
  filterStartDate: Date | undefined;
  filterEndDate: Date | undefined;
  filterLeaveType: string | undefined;
  filterStatus: string | undefined;
  users: UserModel[] = [];

  // Exposing enum keys as arrays for the template
  leaveTypeKeys = Object.keys(LeaveTypeEnum).filter((key) =>
    isNaN(Number(key))
  );
  requestStatusKeys = Object.keys(RequestStatusEnum).filter((key) =>
    isNaN(Number(key))
  );

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.getLeaves();
    this.getUsers();
  }

  getLeaves(): void {
    this.leaveService.getPendingLeaveRequests().subscribe({
      next: (leaves) => {
        this.leaves = leaves;
        this.filteredLeaves = leaves;
      },
      error: (error) => {
        console.error('Error fetching leaves', error);
      },
    });
  }

  getUsers(): void {
    // Implement the logic to fetch users or adjust as needed
  }

  applyFilter(): void {
    this.filteredLeaves = this.leaves.filter((leave) => {
      const leaveTypeMatch =
        !this.filterLeaveType ||
        leave.leaveType ===
          LeaveTypeEnum[this.filterLeaveType as keyof typeof LeaveTypeEnum];
      const statusMatch =
        !this.filterStatus ||
        leave.requestStatus ===
          RequestStatusEnum[
            this.filterStatus as keyof typeof RequestStatusEnum
          ];

      return (
        (!this.filterUserId || leave.user.id === this.filterUserId) &&
        (!this.filterStartDate ||
          new Date(leave.startDate) >= this.filterStartDate) &&
        (!this.filterEndDate ||
          new Date(leave.endDate) <= this.filterEndDate) &&
        leaveTypeMatch &&
        statusMatch
      );
    });
  }

  clearFilters(): void {
    this.filterUserId = undefined;
    this.filterStartDate = undefined;
    this.filterEndDate = undefined;
    this.filterLeaveType = undefined;
    this.filterStatus = undefined;
    this.filteredLeaves = this.leaves;
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString();
  }
}
