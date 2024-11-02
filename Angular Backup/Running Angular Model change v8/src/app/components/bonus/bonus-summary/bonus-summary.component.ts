import { Component, OnInit } from '@angular/core';
import { BonusService } from '../../../services/bonus.service';
import { BonusModel } from '../../../models/bonus.model';

@Component({
  selector: 'app-bonus-summary',
  templateUrl: './bonus-summary.component.html',
  styleUrls: ['./bonus-summary.component.css'],
})
export class BonusSummaryComponent implements OnInit {
  bonusData: BonusModel[] = [];
  totalBonuses: number = 0;
  averageBonus: number = 0;
  errorMessage: string = '';

  constructor(private bonusService: BonusService) {}

  ngOnInit(): void {
    this.loadBonusSummary();
  }

  loadBonusSummary(): void {
    this.bonusService.getAllBonuses().subscribe({
      next: (bonuses) => {
        console.log('Bonuses received:', bonuses); // Check the actual API response
        this.bonusData = bonuses;
      },
      error: (err) => {
        console.error('Error loading bonuses:', err);
      },
    });
  }

  calculateSummary(bonuses: BonusModel[]): void {
    this.totalBonuses = bonuses.length;
    this.averageBonus =
      bonuses.reduce((sum, bonus) => sum + bonus.bonusAmount, 0) /
      bonuses.length;
  }
}
