
import { RoleEnum } from './role.enum';

export class UserModel {
  id?: number;
  fullName!: string;
  email!: string;
  password!: string;
  address?: string;
  gender?: string;
  dateOfBirth?: Date;
  nationalId?: string;
  contact?: string;
  basicSalary?: number;
  joinedDate?: Date;
  isActive?: boolean;
  profilePhoto?: string;
  updatedAt?: Date;

  role?: RoleEnum;
}

