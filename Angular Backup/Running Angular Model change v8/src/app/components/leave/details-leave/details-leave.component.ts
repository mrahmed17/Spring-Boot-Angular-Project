import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeaveService } from '../../../services/leave.service';
import { LeaveModel } from '../../../models/leave.model';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';
import { RequestStatusEnum } from '../../../models/request-status.enum';

@Component({
  selector: 'app-details-leave',
  templateUrl: './details-leave.component.html',
  styleUrls: ['./details-leave.component.css'],
})
export class DetailsLeaveComponent implements OnInit {
  leaveDetails!: LeaveModel;

  constructor(
    private leaveService: LeaveService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const leaveId = Number(this.route.snapshot.paramMap.get('id'));
    this.getLeaveDetails(leaveId);
  }

  getLeaveDetails(leaveId: number): void {
    this.leaveService.getLeaveById(leaveId).subscribe(
      (leave: LeaveModel) => {
        this.leaveDetails = leave;
      },
      (error) => {
        console.error('Error fetching leave details', error);
      }
    );
  }

  getLeaveTypeName(leaveType: LeaveTypeEnum): string {
    switch (leaveType) {
      case LeaveTypeEnum.UNPAID:
        return 'Unpaid Leave';
      case LeaveTypeEnum.RESERVE:
        return 'Reserve Leave';
      default:
        return 'Unknown Leave Type';
    }
  }

  getRequestStatusName(requestStatus: RequestStatusEnum): string {
    switch (requestStatus) {
      case RequestStatusEnum.PENDING:
        return 'Pending';
      case RequestStatusEnum.APPROVED:
        return 'Approved';
      case RequestStatusEnum.REJECTED:
        return 'Rejected';
      default:
        return 'Unknown Status';
    }
  }

  goBack(): void {
    window.history.back();
  }
}
