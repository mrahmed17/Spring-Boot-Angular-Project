
import { UserModel } from './user.model';

export class AdvanceSalaryModel {
  id!: number;
  advanceSalary!: number;
  reason!: string;
  advanceDate!: Date;

  user!: UserModel;
}
