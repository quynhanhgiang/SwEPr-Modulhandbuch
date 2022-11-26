import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditManualModulesComponent } from './edit-manual-modules.component';

describe('EditManualModulesComponent', () => {
  let component: EditManualModulesComponent;
  let fixture: ComponentFixture<EditManualModulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditManualModulesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualModulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
