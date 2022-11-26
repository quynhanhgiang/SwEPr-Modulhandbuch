import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditManualGeneralComponent } from './edit-manual-general.component';

describe('EditManualGeneralComponent', () => {
  let component: EditManualGeneralComponent;
  let fixture: ComponentFixture<EditManualGeneralComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditManualGeneralComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
