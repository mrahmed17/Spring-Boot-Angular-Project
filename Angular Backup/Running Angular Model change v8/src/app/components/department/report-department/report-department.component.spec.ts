import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportDepartmentComponent } from './report-department.component';

describe('ReportDepartmentComponent', () => {
  let component: ReportDepartmentComponent;
  let fixture: ComponentFixture<ReportDepartmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportDepartmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportDepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
