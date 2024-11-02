

import { MonthEnum } from "./month.enum";
import { UserModel } from "./user.model";

export class BonusModel{

  id?: number;
  bonusAmount!: number;
  bonusDate!: Date;
  year!: number;

  advanceMonth!: MonthEnum;

  user!: UserModel;
}



