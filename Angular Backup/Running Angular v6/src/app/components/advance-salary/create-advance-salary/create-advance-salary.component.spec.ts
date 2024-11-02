import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAdvanceSalaryComponent } from './create-advance-salary.component';

describe('CreateAdvanceSalaryComponent', () => {
  let component: CreateAdvanceSalaryComponent;
  let fixture: ComponentFixture<CreateAdvanceSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateAdvanceSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAdvanceSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
