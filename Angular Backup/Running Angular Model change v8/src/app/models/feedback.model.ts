import { MonthEnum } from "./month.enum";
import { UserModel } from "./user.model";

export class FeedbackModel{

 id!: number;
 date!: Date;
 rating!: string;
 comment!: string;

 user!: UserModel;


}

