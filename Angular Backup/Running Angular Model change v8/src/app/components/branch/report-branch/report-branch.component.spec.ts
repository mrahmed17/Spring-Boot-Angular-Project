import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBranchComponent } from './report-branch.component';

describe('ReportBranchComponent', () => {
  let component: ReportBranchComponent;
  let fixture: ComponentFixture<ReportBranchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportBranchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportBranchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
