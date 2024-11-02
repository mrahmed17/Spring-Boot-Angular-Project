import { Component, OnInit } from '@angular/core';
import { SalaryModel } from '../../../models/salary.model';
import { SalaryService } from '../../../services/salary.service';

@Component({
  selector: 'app-details-salary',
  templateUrl: './details-salary.component.html',
  styleUrl: './details-salary.component.css',
})
export class DetailsSalaryComponent implements OnInit {
  salaries: SalaryModel[] = [];
  userId: number = 1;
  year: number = new Date().getFullYear();

  constructor(private salaryService: SalaryService) {}

  ngOnInit(): void {
    this.salaryService
      .getSalariesByUserAndYear(this.userId, this.year)
      .subscribe(
        (data: SalaryModel[]) => {
          this.salaries = data;
        },
        (error) => {
          console.error('Error fetching salaries', error);
        }
      );
  }
}