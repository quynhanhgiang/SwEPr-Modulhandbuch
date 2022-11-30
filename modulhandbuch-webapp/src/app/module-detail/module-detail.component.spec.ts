import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleDetailComponent } from './module-detail.component';

describe('ModuleDetailComponent', () => {
  let component: ModuleDetailComponent;
  let fixture: ComponentFixture<ModuleDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModuleDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModuleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
  * Testfall A6.2.A:UT1 Testen, ob Komponente erzeugt wird.
  */
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
