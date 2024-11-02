

import { MonthEnum } from './month.enum';
import { UserModel } from './user.model';

export class AdvanceSalaryModel {
  id?: number;
  advanceSalary!: number;
  reason!: string;
  advanceDate!: Date;
  year!: number;

  advanceMonth!: MonthEnum;

  user!: UserModel;
}
