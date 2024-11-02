import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SalaryService } from '../../../services/salary.service';
import { SalaryModel } from '../../../models/salary.model';
import { MonthEnum } from '../../../models/month.enum';

@Component({
  selector: 'app-createsalary',
  templateUrl: './createsalary.component.html',
  styleUrls: ['./createsalary.component.css'],
})
export class CreatesalaryComponent implements OnInit {
  salaryForm: FormGroup;
  MonthEnum = MonthEnum;

  constructor(
    private fb: FormBuilder,
    private salaryService: SalaryService,
    private router: Router
  ) {
    this.salaryForm = this.fb.group({
      paymentDate: [null, Validators.required],
      medicare: [0, Validators.required],
      providentFund: [0, Validators.required],
      insurance: [0, Validators.required],
      transportAllowance: [0, Validators.required],
      telephoneSubsidy: [0, Validators.required],
      utilityAllowance: [0, Validators.required],
      domesticAllowance: [0, Validators.required],
      lunchAllowance: [0, Validators.required],
      tax: [0, Validators.required],
      netSalary: [{ value: 0, disabled: true }, Validators.required],
      year: [new Date().getFullYear(), Validators.required],
      payrollMonth: [null, Validators.required],
      user: this.fb.group({ id: [1, Validators.required] }),
    });

  }

  ngOnInit() {
    // Recalculate net salary when any of the related fields change
    this.salaryForm.valueChanges.subscribe((values) => {
      this.calculateNetSalary(values);
    });
  }

  calculateNetSalary(values: any) {
    const grossSalary =
      +values.transportAllowance +
      +values.telephoneSubsidy +
      +values.utilityAllowance +
      +values.domesticAllowance +
      +values.lunchAllowance;

    const deductions =
      +values.medicare +
      +values.providentFund +
      +values.insurance +
      +values.tax;

    const netSalary = grossSalary - deductions;

    // Update the netSalary field
    this.salaryForm.get('netSalary')?.setValue(netSalary, { emitEvent: false });
  }

  onSubmit() {
    if (this.salaryForm.valid) {
      const salary: SalaryModel = this.salaryForm.getRawValue(); // Get raw value to include disabled fields
      this.salaryService.createSalary(salary).subscribe(
        (response) => {
          console.log('Salary created successfully!', response);
          this.router.navigate(['/salary/details']);
        },
        (error) => {
          console.error('Error creating salary', error);
        }
      );
    }
  }

  get monthKeys(): Array<string> {
    return Object.keys(MonthEnum).filter((key) => isNaN(Number(key)));
  }

  getMonthValue(month: string): number {
    return MonthEnum[month as keyof typeof MonthEnum];
  }
}
