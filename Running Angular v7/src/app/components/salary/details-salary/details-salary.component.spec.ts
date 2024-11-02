import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsSalaryComponent } from './details-salary.component';

describe('DetailsSalaryComponent', () => {
  let component: DetailsSalaryComponent;
  let fixture: ComponentFixture<DetailsSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailsSalaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
