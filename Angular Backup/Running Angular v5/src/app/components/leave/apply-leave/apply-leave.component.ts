import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LeaveTypeEnum } from '../../../models/leave-type.enum';
import { MonthEnum } from '../../../models/month.enum';
import { RequestStatusEnum } from '../../../models/request-status.enum';
import { UserModel } from '../../../models/user.model';
import { LeaveModel } from '../../../models/leave.model';
import { LeaveService } from '../../../services/leave.service';

@Component({
  selector: 'app-apply-leave',
  templateUrl: './apply-leave.component.html',
  styleUrls: ['./apply-leave.component.css'],
})
export class ApplyLeaveComponent implements OnInit {
  leaveForm!: FormGroup;
  leaveTypes = Object.keys(LeaveTypeEnum).filter((key) => isNaN(Number(key)));
  months = Object.keys(MonthEnum).filter((key) => isNaN(Number(key)));
  requestStatuses = Object.values(RequestStatusEnum);
  user!: UserModel;
  leaveModel!: LeaveModel;

  constructor(private leaveService: LeaveService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initLeaveForm();
  }

  initLeaveForm() {
    this.leaveForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      leaveMonth: ['', Validators.required],
      leaveType: ['', Validators.required],
      reason: ['', Validators.required],
    });
  }

  applyLeave() {
    if (this.leaveForm.valid) {
      this.leaveModel = {
        ...this.leaveForm.value,
        year: new Date().getFullYear(),
        requestDate: new Date(),
        remainingLeave: 0, // This will likely be calculated on the backend
        requestStatus: RequestStatusEnum.PENDING,
        user: this.user, // Replace with the actual logged-in user
      };

      this.leaveService.applyLeave(this.leaveModel).subscribe({
        next: (response) => {
          alert('Leave request submitted successfully!');
          this.leaveForm.reset();
        },
        error: (error) => {
          console.error(error);
          alert('Error while submitting the leave request.');
        },
      });
    } else {
      alert('Please fill out all required fields.');
    }
  }
}
