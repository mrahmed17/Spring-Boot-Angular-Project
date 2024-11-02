import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusCreateComponent } from './bonus-create.component';

describe('BonusCreateComponent', () => {
  let component: BonusCreateComponent;
  let fixture: ComponentFixture<BonusCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BonusCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonusCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
