import { Component, OnInit } from '@angular/core';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { NotificationService } from '../../../services/notification.service';
import { Router } from '@angular/router';
import { UserModel } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-advance-salary',
  templateUrl: './create-advance-salary.component.html',
  styleUrls: ['./create-advance-salary.component.css'],
})
export class CreateAdvanceSalaryComponent implements OnInit {
  users: UserModel[] = [];
  advanceSalary: AdvanceSalaryModel = new AdvanceSalaryModel();
  errorMessage: string | null = null;

  constructor(
    private advanceSalaryService: AdvanceSalaryService,
    private userService: UserService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users: UserModel[]) => {
        this.users = users;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load users.';
      },
    });
  }

  onSubmit(form: NgForm) {
    if (form.invalid) {
      return;
    }

    this.advanceSalaryService
      .createAdvanceSalary(this.advanceSalary)
      .subscribe({
        next: (response) => {
          this.notificationService.showNotify(
            'Advance Salary Created successfully!',
            'success'
          );
          this.router.navigate(['/advance-salary/list']);
        },
        error: (err) => {
          this.notificationService.showNotify(
            'Error creating advance salary!',
            'error'
          );
        },
      });
  }

  cancel(): void {
    this.router.navigate(['/advance-salary/list']);
  }
}
