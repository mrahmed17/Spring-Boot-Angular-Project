import { BranchModel } from './branch.model';

export class DepartmentModel {
  id!: number;
  departmentName!: string;
  numOfEmployees!: number;
  photo?: string;

  branch!: BranchModel;


}
