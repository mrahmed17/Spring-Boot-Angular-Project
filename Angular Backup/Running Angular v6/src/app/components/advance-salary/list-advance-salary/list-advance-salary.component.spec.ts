import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdvanceSalaryComponent } from './list-advance-salary.component';

describe('ListAdvanceSalaryComponent', () => {
  let component: ListAdvanceSalaryComponent;
  let fixture: ComponentFixture<ListAdvanceSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListAdvanceSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAdvanceSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
