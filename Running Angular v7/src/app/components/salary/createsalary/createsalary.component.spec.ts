import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatesalaryComponent } from './createsalary.component';

describe('CreatesalaryComponent', () => {
  let component: CreatesalaryComponent;
  let fixture: ComponentFixture<CreatesalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatesalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatesalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
