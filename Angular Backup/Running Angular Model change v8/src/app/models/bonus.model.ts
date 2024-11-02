
import { UserModel } from "./user.model";

export class BonusModel{

  id!: number;
  bonusAmount!: number;
  bonusDate!: Date;

  user!: UserModel;
}



