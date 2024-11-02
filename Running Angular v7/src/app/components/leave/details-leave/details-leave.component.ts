import { Component, OnInit, Input } from '@angular/core';
import { LeaveModel } from '../../../models/leave.model';
import { LeaveService } from '../../../services/leave.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MonthEnum } from '../../../models/month.enum';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';

@Component({
  selector: 'app-details-leave',
  templateUrl: './details-leave.component.html',
  styleUrls: ['./details-leave.component.css'],
})
export class DetailsLeaveComponent implements OnInit {
  leaveDetails!: LeaveModel | null; // Allow null for error or loading state

  // To display enums as strings
  leaveTypes = LeaveTypeEnum;
  months = MonthEnum;

  constructor(
    private leaveService: LeaveService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const leaveId = this.route.snapshot.paramMap.get('leaveId');
    if (leaveId) {
      this.getLeaveDetails(Number(leaveId));
    } else {
      alert('Invalid leave ID provided.');
      this.router.navigate(['/leaves']);
    }
  }

  getLeaveDetails(leaveId: number) {
    this.leaveService.getLeaveById(leaveId).subscribe({
      next: (data) => {
        this.leaveDetails = data;
      },
      error: (error) => {
        console.error('Error fetching leave details', error);
        alert('Failed to load leave details.');
        this.leaveDetails = null; // Handle the case where no data is available
        this.router.navigate(['/leaves']); 
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
