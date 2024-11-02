
import { DepartmentModel } from './department.model';
import { RoleEnum } from './role.enum';

export class UserModel {
  id!: number;
  fullName!: string;
  email!: string;
  password!: string;
  address?: string;
  gender!: string;
  dateOfBirth?: Date;
  nationalId?: string;
  contact?: string;
  basicSalary?: number;
  joinedDate?: Date;
  profilePhoto?: string;
  isActive!: boolean;
  updatedAt!: Date;

  role!: RoleEnum;

  department!: DepartmentModel;
  

}


