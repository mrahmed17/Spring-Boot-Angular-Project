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
  requestStatuses = Object.values(RequestStatusEnum).filter((key) =>
    isNaN(Number(key))
  );
  user!: UserModel;
  leaveModel!: LeaveModel;

  constructor(private leaveService: LeaveService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initLeaveForm();
    // this.user = this.authService.getLoggedInUser();
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

  // applyLeave() {
  //   if (this.leaveForm.valid) {
  //     const placeholderUser: UserModel = {
  //       id: 1,
  //       fullName: 'Md. Raju Ahmed',
  //       email: 'raju@mail.com',
  //       password: '123456',
  // address:'Near Lalbagh Kella',
  //       gender: 'Male',
  // dateOfBirth: 02-02-2022,
  // nationalid: '123546',
  // contact: '2323',
  // basicSalary: 123456,
  // joinedDate: ,
  // isActive: true,
  // profilePhoto: 'http://www.github.com/mrahmed17',
  // updatedAt: 02-02-2002,

  // role: "ADMIN",

  // department:"Department"

  //     };

  //     this.leaveModel = {
  //       ...this.leaveForm.value,
  //       year: new Date().getFullYear(),
  //       requestDate: new Date(),
  //       remainingLeave: 0, 
  //       requestStatus: RequestStatusEnum.PENDING,
  //       user: placeholderUser,
  //     };

  //     this.leaveService.applyLeave(this.leaveModel).subscribe({
  //       next: (response) => {
  //         alert('Leave request submitted successfully!');
  //         this.leaveForm.reset();
  //       },
  //       error: (error) => {
  //         console.error(error);
  //         alert('Error while submitting the leave request.');
  //       },
  //     });
  //   } else {
  //     alert('Please fill out all required fields.');
  //   }
  // }


}
