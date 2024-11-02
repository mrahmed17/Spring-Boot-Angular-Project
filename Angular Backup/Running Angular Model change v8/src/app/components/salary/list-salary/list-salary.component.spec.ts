import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSalaryComponent } from './list-salary.component';

describe('ListSalaryComponent', () => {
  let component: ListSalaryComponent;
  let fixture: ComponentFixture<ListSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
