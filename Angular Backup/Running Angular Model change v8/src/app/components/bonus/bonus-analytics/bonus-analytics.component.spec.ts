import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusAnalyticsComponent } from './bonus-analytics.component';

describe('BonusAnalyticsComponent', () => {
  let component: BonusAnalyticsComponent;
  let fixture: ComponentFixture<BonusAnalyticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BonusAnalyticsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonusAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
