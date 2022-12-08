import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { EditManualGeneralComponent } from './edit-manual-general.component';

describe('EditManualGeneralComponent', () => {
  let component: EditManualGeneralComponent;
  let fixture: ComponentFixture<EditManualGeneralComponent>;

  const fakeActivatedRoute = {
    snapshot: { parent: { paramMap: {id: 1} } }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    providers: [
      {provide: ActivatedRoute, useValue: fakeActivatedRoute},
      FormBuilder
    ],
    imports: [
      HttpClientModule
    ],
      declarations: [ EditManualGeneralComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * Testfall A10.3:UT2 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
