import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetModuleManualsComponent } from './get-module-manuals.component';

describe('GetModuleManualsComponent', () => {
  let component: GetModuleManualsComponent;
  let fixture: ComponentFixture<GetModuleManualsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetModuleManualsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetModuleManualsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
