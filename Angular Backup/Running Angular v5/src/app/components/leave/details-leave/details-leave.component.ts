import { Component, OnInit, Input } from '@angular/core';
import { LeaveModel } from '../../../models/leave.model';
import { LeaveService } from '../../../services/leave.service';
import { ActivatedRoute } from '@angular/router';
import { MonthEnum } from '../../../models/month.enum';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';

@Component({
  selector: 'app-details-leave',
  templateUrl: './details-leave.component.html',
  styleUrls: ['./details-leave.component.css'],
})
export class DetailsLeaveComponent implements OnInit {
  leaveDetails!: LeaveModel;

  // To display enums as strings
  leaveTypes = LeaveTypeEnum;
  months = MonthEnum;

  constructor(
    private leaveService: LeaveService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const leaveId = this.route.snapshot.paramMap.get('leaveId');
    if (leaveId) {
      this.getLeaveDetails(Number(leaveId));
    }
  }

  getLeaveDetails(leaveId: number) {
    this.leaveService.getLeaveById(leaveId).subscribe({
      next: (data) => {
        this.leaveDetails = data;
      },
      error: (error) => {
        console.error('Error fetching leave details', error);
      },
    });
  }

  // Convert enum value to readable string
  getMonthName(month: MonthEnum): string {
    return MonthEnum[month];
  }

  getLeaveTypeName(leaveType: LeaveTypeEnum): string {
    return LeaveTypeEnum[leaveType];
  }
}
