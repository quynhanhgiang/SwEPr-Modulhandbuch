import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateModuleManualComponent } from './create-module-manual.component';

describe('CreateModuleManualComponent', () => {
  let component: CreateModuleManualComponent;
  let fixture: ComponentFixture<CreateModuleManualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateModuleManualComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateModuleManualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
