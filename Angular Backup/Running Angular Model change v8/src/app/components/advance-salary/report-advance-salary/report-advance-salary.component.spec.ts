import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportAdvanceSalaryComponent } from './report-advance-salary.component';

describe('ReportAdvanceSalaryComponent', () => {
  let component: ReportAdvanceSalaryComponent;
  let fixture: ComponentFixture<ReportAdvanceSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportAdvanceSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportAdvanceSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
