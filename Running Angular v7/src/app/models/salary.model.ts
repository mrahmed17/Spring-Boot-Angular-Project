

import { AdvanceSalaryModel } from './advance-salary.model';
import { AttendanceModel } from './attendance.model';
import { BonusModel } from './bonus.model';
import { LeaveModel } from './leave.model';
import { MonthEnum } from './month.enum';
import { UserModel } from './user.model';

export class SalaryModel {
  id?: number;
  paymentDate!: Date;
  medicare!: number;
  providentFund!: number;
  insurance!: number;
  transportAllowance!: number;
  telephoneSubsidy!: number;
  utilityAllowance!: number;
  domesticAllowance!: number;
  lunchAllowance!: number;
  tax!: number;
  netSalary!: number;
  year!: number;

  overTime!: AttendanceModel;

  payrollMonth!: MonthEnum;

  user!: UserModel;

  advanceSalary!: AdvanceSalaryModel;

  bonuses!: BonusModel;

  leaves!: LeaveModel;
}
