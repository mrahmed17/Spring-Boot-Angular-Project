import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
})
export class CreateUserComponent {
  user: UserModel = new UserModel();
  profilePhoto?: File;

  constructor(
    private userService: UserService,
    private notification: NotificationService,
    private router: Router
  ) {}

  onPhotoSelected(event: any): void {
    this.profilePhoto = event.target.files[0];
  }

  onSubmit(form: any): void {
    const formData: FormData = new FormData();
    formData.append('user', JSON.stringify(this.user));
    if (this.profilePhoto) {
      formData.append(
        'profilePhoto',
        this.profilePhoto,
        this.profilePhoto.name
      );
    }

    this.userService.createUser(formData).subscribe({
      next: () => {
        this.notification.showNotify('User created successfully!', 'success');
        this.router.navigate(['/user/list']);
      },
      error: (error) => {
        console.error('Error creating user', error);
        this.notification.showNotify('Error creating user', 'error');
      },
    });
  }
}
