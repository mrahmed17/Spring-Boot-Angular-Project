import { Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { UserModel } from '../../../models/user.model';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';
import { RequestStatusEnum } from '../../../models/request-status.enum';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-list-leave',
  templateUrl: './list-leave.component.html',
  styleUrls: ['./list-leave.component.css'],
})
export class ListLeaveComponent implements OnInit {
  leaves: LeaveModel[] = [];
  filteredLeaves: LeaveModel[] = [];
  filterUserid!: number;
  filterStartDate?: Date;
  filterEndDate?: Date;
  filterLeaveType?: LeaveTypeEnum;
  filterStatus?: RequestStatusEnum;
  users: UserModel[] = [];

  leaveTypeKeys = Object.keys(LeaveTypeEnum).filter((key) =>
    isNaN(Number(key))
  );
  requestStatusKeys = Object.keys(RequestStatusEnum).filter((key) =>
    isNaN(Number(key))
  );

  constructor(
    private leaveService: LeaveService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.getLeaves();
    this.getUsers();
  }

  getLeaves(): void {
    this.leaveService.getPendingLeaveRequests().subscribe({
      next: (leaves: LeaveModel[]) => {
        this.leaves = leaves;
        this.filteredLeaves = leaves;
      },
      error: (error) => {
        console.error('Error fetching leaves', error);
      },
    });
  }

  getUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users: UserModel[]) => {
        this.users = users;
      },
      error: (error) => {
        console.error('Error fetching users', error);
      },
    });
  }

  // applyFilter(): void {
  //   this.filteredLeaves = this.leaves.filter((leave) => {
  //     const leaveTypeMatch =
  //       !this.filterLeaveType || leave.leaveType === this.filterLeaveType;
  //     const statusMatch =
  //       !this.filterStatus || leave.requestStatus === this.filterStatus;

  //     return (
  //       (!this.filterUserId || leave.user.id === this.filterUserId) &&
  //       (!this.filterStartDate ||
  //         new Date(leave.startDate) >= this.filterStartDate) &&
  //       (!this.filterEndDate ||
  //         new Date(leave.endDate) <= this.filterEndDate) &&
  //       leaveTypeMatch &&
  //       statusMatch
  //     );
  //   });
  // }

  clearFilters(): void {
    // this.filterUserId = undefined;
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
