import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusHistoryComponent } from './bonus-history.component';

describe('BonusHistoryComponent', () => {
  let component: BonusHistoryComponent;
  let fixture: ComponentFixture<BonusHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BonusHistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonusHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
