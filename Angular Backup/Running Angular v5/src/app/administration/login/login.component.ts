import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faEnvelope, faLock, faSignInAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  faSignInAlt = faSignInAlt;
  faEnvelope = faEnvelope;
  faLock = faLock;

  loginForm!: FormGroup;

  constructor(
    // private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  // onSubmit(): void {
  //   if (this.loginForm.valid) {
  //     const credentials = this.loginForm.value;
  //     this.authService.login(credentials).subscribe({
  //       next: (res) => {
  //         console.log('User logged in successfully:', res);

  //         this.authService.storeToken(res.token);
  //         const role = this.authService.getUserRole();
  //         this.router.navigate(['/userprofile']);
  //         // Navigate to a protected route after login
  //       },
  //       error: (err) => {
  //         console.error('Error logging in:', err); // Display an error message to the user
  //         alert('Login failed. Please check your credentials and try again.');
  //       },
  //     });
  //   } else {
  //     alert('Please fill out all required fields.');
  //   }
  // }
}
