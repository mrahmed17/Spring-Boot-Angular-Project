import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgetpassword',
  templateUrl: './forgetpassword.component.html',
  styleUrl: './forgetpassword.component.css',
})
export class ForgetpasswordComponent implements OnInit {
  forgotPasswordForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    // private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initForgotPasswordForm();
  }

  initForgotPasswordForm() {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  get email() {
    return this.forgotPasswordForm.get('email')!;
  }

  // onSubmit() {
  //   if (this.forgotPasswordForm.valid) {
  //     const email = this.forgotPasswordForm.value.email;
  //     this.authService.forgetPassword(email).subscribe({
  //       next: (response) => {
  //         alert('Password reset link sent to your email.');
  //         this.forgotPasswordForm.reset();
  //       },
  //       error: (error) => {
  //         console.error('Error sending password reset link:', error);
  //         alert('Failed to send password reset link. Please try again.');
  //       },
  //     });
  //   }
  // }
}
