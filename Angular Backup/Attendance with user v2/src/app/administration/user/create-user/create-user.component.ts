import { Component } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { RoleEnum } from '../../../models/role.enum';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css',
})
export class CreateUserComponent {
  user: UserModel = new UserModel();
  roles = Object.values(RoleEnum);

  constructor(private userService: UserService, private router: Router,
    private notificationService: NotificationService
  ) {}

  addUser(): void {
    this.userService.saveUser(this.user).subscribe(() => {
      this.router.navigate(['/user/list']);
       alert('User created successfully.');
       this.notificationService.showNotify('User created successfully.');
    });
  }
}
