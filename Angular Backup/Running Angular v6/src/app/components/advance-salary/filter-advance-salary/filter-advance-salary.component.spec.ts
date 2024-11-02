import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterAdvanceSalaryComponent } from './filter-advance-salary.component';

describe('FilterAdvanceSalaryComponent', () => {
  let component: FilterAdvanceSalaryComponent;
  let fixture: ComponentFixture<FilterAdvanceSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilterAdvanceSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilterAdvanceSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
