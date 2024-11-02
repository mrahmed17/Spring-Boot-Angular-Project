import { AdvanceSalaryModel } from "./advance-salary.model";
import { BonusModel } from "./bonus.model";
import { SalaryModel } from "./salary.model";
import { UserModel } from "./user.model";

export class PaymentReceiptModel {
  id!: number;

  paymentDate!: Date;
  totalPaidAmount!: number;
  deductions!: number;
  netPaidAmount!: number;
  status!: boolean; // Payment status is Paid or Unpaid
  paymentReceiver!: UserModel;
  
  salary!: SalaryModel;
  
}
