import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAttendanceComponent } from './list-attendance.component';

describe('ListAttendanceComponent', () => {
  let component: ListAttendanceComponent;
  let fixture: ComponentFixture<ListAttendanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListAttendanceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAttendanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
