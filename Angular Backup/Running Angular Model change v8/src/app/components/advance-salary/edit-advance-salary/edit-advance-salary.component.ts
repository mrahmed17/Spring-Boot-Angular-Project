import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { NotificationService } from '../../../services/notification.service';
import { UserModel } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-edit-advance-salary',
  templateUrl: './edit-advance-salary.component.html',
  styleUrls: ['./edit-advance-salary.component.css'],
})
export class EditAdvanceSalaryComponent implements OnInit {
  advanceSalary: AdvanceSalaryModel = new AdvanceSalaryModel();
  users: UserModel[] = [];
  errorMessage: string | null = null; 
  private advanceSalaryId!: number;

  constructor(
    private advanceSalaryService: AdvanceSalaryService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.advanceSalaryId = +params['id'];
      this.loadAdvanceSalary();
      this.loadUsers(); 
    });
  }

  loadAdvanceSalary(): void {
    this.advanceSalaryService
      .getAdvanceSalaryById(this.advanceSalaryId)
      .subscribe({
        next: (salary) => {
          this.advanceSalary = salary;
        },
        error: (err) => {
          this.errorMessage = 'Failed to load advance salary record.';
        },
      });
  }

  loadUsers(): void {
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
      .updateAdvanceSalary(this.advanceSalaryId, this.advanceSalary)
      .subscribe({
        next: (response) => {
          this.notificationService.showNotify(
            'Advance Salary updated successfully!',
            'success'
          );
          this.router.navigate(['/advance-salary/list']); 
        },
        error: (err) => {
          this.notificationService.showNotify(
            'Error updating advance salary!',
            'error'
          );
        },
      });
  }

  cancel(): void {
    this.router.navigate(['/advance-salary/list']); 
  }
}
