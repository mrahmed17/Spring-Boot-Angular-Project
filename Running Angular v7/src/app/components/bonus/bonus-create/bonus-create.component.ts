import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BonusService } from '../../../services/bonus.service';
import { BonusModel } from '../../../models/bonus.model';


@Component({
  selector: 'app-bonus-create',
  templateUrl: './bonus-create.component.html',
  styleUrls: ['./bonus-create.component.css'],
})
export class BonusCreateComponent implements OnInit {
  bonusForm: FormGroup;
  submitting = false;

  constructor(
    private fb: FormBuilder,
    private bonusService: BonusService,
    private router: Router
  ) {
    this.bonusForm = this.fb.group({
      amount: ['', [Validators.required, Validators.min(1)]],
      reason: ['', Validators.required],
      date: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  get formControls() {
    return this.bonusForm.controls;
  }

  onSubmit(): void {
    if (this.bonusForm.invalid) {
      return;
    }

    this.submitting = true;
    const bonus: BonusModel = this.bonusForm.value;

    this.bonusService.createBonus(bonus).subscribe(
      () => {
        this.router.navigate(['/bonus/summary']);
      },
      (error) => {
        console.error('Error creating bonus:', error);
        this.submitting = false;
      }
    );
  }
}
