import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';
import { RoleEnum } from '../../../models/role.enum';
import {
  faCalendarAlt,
  faCalendarDay,
  faDollarSign,
  faEnvelope,
  faHome,
  faIdCard,
  faImage,
  faKey,
  faPhone,
  faUser,
  faUserEdit,
  faUserTag,
  faVenusMars,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  faUser = faUser;
  faEnvelope = faEnvelope;
  faKey = faKey;
  faHome = faHome;
  faVenusMars = faVenusMars;
  faCalendarAlt = faCalendarAlt;
  faIdCard = faIdCard;
  faPhone = faPhone;
  faDollarSign = faDollarSign;
  faCalendarDay = faCalendarDay;
  faUserTag = faUserTag;
  faImage = faImage;
  faUserEdit = faUserEdit;

  editUserForm!: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  selectedFile: File | null = null;
  userId!: number;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationService
  ) {
    this.editUserForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      address: [''],
      gender: [''],
      dateOfBirth: ['', Validators.required],
      nationalId: ['', Validators.required],
      contact: ['', Validators.required],
      basicSalary: ['', Validators.required],
      joinedDate: ['', Validators.required],
      role: ['', Validators.required],
      profilePhoto: [null], // Optional for file
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.userId = +id;
      this.loadUser(this.userId);
    }
  }

  loadUser(id: number): void {
    this.userService.getUserById(id).subscribe({
      next: (user) => {
        this.editUserForm.patchValue(user);
      },
      error: (err) => {
        this.errorMessage = 'Error loading user details';
        console.error(err);
      },
    });
  }

  onFileChange(event: any): void {
    if (event.target.files && event.target.files.length) {
      this.selectedFile = event.target.files[0];
      this.editUserForm.patchValue({ profilePhoto: this.selectedFile });
    }
  }

  onSubmit(): void {
    if (this.editUserForm.valid) {
      const user: UserModel = this.editUserForm.value;

      const fileToSend: File | undefined = this.selectedFile || undefined;

      this.userService.updateUser(this.userId, user, fileToSend).subscribe({
        next: (response) => {
          this.successMessage = 'User updated successfully!';
          this.errorMessage = '';
          this.notificationService.showNotify(
            'User updated successfully!',
            'success'
          );
          this.router.navigate(['/user/list']);
        },
        error: (error) => {
          this.errorMessage = 'Failed to update user';
          this.successMessage = '';
        },
      });
    } else {
      this.errorMessage = 'Please fill in all required fields';
      this.successMessage = '';
    }
  }

  cancel(): void {
    this.router.navigate(['/user/list']);
  }
}
