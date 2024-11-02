import { CompanyModel } from './company.model';


export class BranchModel {
  id!: number;
  branchName!: string;
  address!: string;
  city!: string;
  zipCode!: string;
  country!: string;
  createdAt!: Date;
  updateAt!: Date;
  photo?: string;

  company!: CompanyModel;

  
}
