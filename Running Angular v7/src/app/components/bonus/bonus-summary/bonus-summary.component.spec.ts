import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusSummaryComponent } from './bonus-summary.component';

describe('BonusSummaryComponent', () => {
  let component: BonusSummaryComponent;
  let fixture: ComponentFixture<BonusSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BonusSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonusSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
