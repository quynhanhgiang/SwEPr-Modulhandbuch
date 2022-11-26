import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditManualComponent } from './edit-manual.component';

describe('EditManualComponent', () => {
  let component: EditManualComponent;
  let fixture: ComponentFixture<EditManualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditManualComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
