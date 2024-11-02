import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  userId!: number;
  user!: UserModel;
  isLoading: boolean = true;
  profilePhoto?: File;

  constructor(
    private userService: UserService,
    private notification: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id')!;
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.user = user;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading user', error);
        this.notification.showNotify('Error loading user', 'error');
        this.isLoading = false;
      },
    });
  }

  onPhotoSelected(event: any): void {
    this.profilePhoto = event.target.files[0];
  }

  onSubmit(form: any): void {
    if (form.invalid) {
      return;
    }

    const formData: FormData = new FormData();
    formData.append('user', JSON.stringify(this.user));
    if (this.profilePhoto) {
      formData.append(
        'profilePhoto',
        this.profilePhoto,
        this.profilePhoto.name
      );
    }

    this.userService.updateUser(this.userId, formData).subscribe({
      next: () => {
        this.notification.showNotify('User updated successfully', 'success');
        this.router.navigate(['/user/list']);
      },
      error: (error) => {
        console.error('Error updating user', error);
        this.notification.showNotify('Error updating user', 'error');
      },
    });
  }
}
