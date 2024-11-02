import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusManagementComponent } from './bonus-management.component';

describe('BonusManagementComponent', () => {
  let component: BonusManagementComponent;
  let fixture: ComponentFixture<BonusManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BonusManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonusManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
