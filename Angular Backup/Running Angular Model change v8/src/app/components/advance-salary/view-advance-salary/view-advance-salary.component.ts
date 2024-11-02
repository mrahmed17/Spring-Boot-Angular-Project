import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-view-advance-salary',
  templateUrl: './view-advance-salary.component.html',
  styleUrls: ['./view-advance-salary.component.css'],
})
export class ViewAdvanceSalaryComponent implements OnInit {
  advanceSalary: AdvanceSalaryModel | null = null;
  errorMessage: string | null = null;
  isLoading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private advanceSalaryService: AdvanceSalaryService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAdvanceSalaryDetails();
  }

  getAdvanceSalaryDetails(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isLoading = true;
      this.advanceSalaryService.getAdvanceSalaryById(+id).subscribe({
        next: (salary) => {
          this.advanceSalary = salary;
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = 'Failed to load advance salary details.';
          this.isLoading = false;
          this.notificationService.showNotify(this.errorMessage, 'error');
        },
      });
    }
  }

  backToList(): void {
    this.router.navigate(['/advance-salary/list']);
  }
}
