import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { EditManualModulesComponent } from './edit-manual-modules.component';

describe('EditManualModulesComponent', () => {
  let component: EditManualModulesComponent;
  let fixture: ComponentFixture<EditManualModulesComponent>;

  const fakeActivatedRoute = {
    snapshot: { data: { ... } }
  } as ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        {provide: ActivatedRoute, useValue: fakeActivatedRoute}
      ],
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
