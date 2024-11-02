import { Component, OnInit } from '@angular/core';
import { AdvanceSalaryModel } from '../../../models/advance-salary.model';
import { AdvanceSalaryService } from '../../../services/advancesalary.service';
import { MonthEnum } from '../../../models/month.enum';

@Component({
  selector: 'app-create-advance-salary',
  templateUrl: './create-advance-salary.component.html',
  styleUrls: ['./create-advance-salary.component.css'],
})
export class CreateAdvanceSalaryComponent implements OnInit {
  advanceSalary: AdvanceSalaryModel = new AdvanceSalaryModel();
  months: string[] = []; // Array to hold months

  constructor(private advanceSalaryService: AdvanceSalaryService) {}

  ngOnInit(): void {
    // Populate months array from MonthEnum
    this.months = Object.keys(MonthEnum).filter((key) => isNaN(Number(key))); // Filter out numbers from the enum
  }

  createAdvanceSalary(): void {
    this.advanceSalaryService
      .createAdvanceSalary(this.advanceSalary)
      .subscribe({
        next: (response) => {
          console.log('Advance salary created successfully:', response);
          // Reset form or show success message
        },
        error: (err) => {
          console.error('Error creating advance salary:', err);
          // Handle error (e.g., show error message)
        },
      });
  }
}
