import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportCompanyComponent } from './report-company.component';

describe('ReportCompanyComponent', () => {
  let component: ReportCompanyComponent;
  let fixture: ComponentFixture<ReportCompanyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportCompanyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
