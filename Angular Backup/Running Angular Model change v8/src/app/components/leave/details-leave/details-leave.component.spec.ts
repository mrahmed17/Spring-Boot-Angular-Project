import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsLeaveComponent } from './details-leave.component';

describe('DetailsLeaveComponent', () => {
  let component: DetailsLeaveComponent;
  let fixture: ComponentFixture<DetailsLeaveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailsLeaveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsLeaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
