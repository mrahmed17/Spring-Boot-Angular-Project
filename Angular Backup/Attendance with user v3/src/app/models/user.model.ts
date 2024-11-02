
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
  role?: RoleEnum;
  updatedAt?: Date;
}


// import { RoleEnum } from './role.enum';

// export class UserModel {
//   id: number = 0;
//   fullName: string = '';
//   email: string = '';
//   password: string = '';
//   address: string = '';
//   gender: string = '';
//   dateOfBirth: Date = new Date();
//   nationalId: string = '';
//   contact: string = '';
//   basicSalary: number = 0;
//   joinedDate: Date = new Date();
//   isActive: boolean = true;
//   createdAt: Date = new Date();
//   updatedAt: Date = new Date();
//   profilePhoto: string = '';
//   role: RoleEnum = RoleEnum.EMPLOYEE;
// }
