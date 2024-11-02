

import { LeaveTypeEnum } from './leave-type.enum';
import { RequestStatusEnum } from './request-status.enum';
import { UserModel } from './user.model';

export class LeaveModel {
  id!: number;
  startDate!: Date;
  endDate!: Date;
  requestDate!: Date;
  reason!: string;
  remainingLeave!: number;
  isUnpaid!: boolean;

  leaveType!: LeaveTypeEnum;

  requestStatus!: RequestStatusEnum;

  user!: UserModel;


}
