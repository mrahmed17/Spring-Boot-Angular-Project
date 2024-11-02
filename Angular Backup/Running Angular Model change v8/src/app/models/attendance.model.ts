import { UserModel } from './user.model';

export class AttendanceModel {
  id!: number;

  date!: Date;
  clockInTime!: Date;
  clockOutTime!: Date | null;

  late!: boolean;

  user!: UserModel;

  get status(): string {
    if (!this.clockInTime) {
      return 'Absent';
    }
    if (!this.clockOutTime) {
      return 'Checked In';
    }
    return 'Checked Out';
  }
}
