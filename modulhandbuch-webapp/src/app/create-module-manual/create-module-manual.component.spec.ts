import { DOCUMENT } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { Spo } from '../shared/spo';

import { CreateModuleManualComponent } from './create-module-manual.component';
import { spos } from './mock-spos';

describe('CreateModuleManualComponent', () => {
  let component: CreateModuleManualComponent;
  let fixture: ComponentFixture<CreateModuleManualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        FormBuilder
      ],
      imports: [
        HttpClientModule
      ],
      declarations: [
        CreateModuleManualComponent
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateModuleManualComponent);
    component = fixture.componentInstance;
  });

  /**
   * Testfall A9.2:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A9.2:UT2 Testen, ob obere Checkbox (SPO) aktiviert wird falls bereits SPOs existieren.
   */
   it('should enable the spo-select-checkbox if spos are already existing ', () => {

    let document = TestBed.inject(DOCUMENT);

    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = spos;
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    fixture.detectChanges();

    let chk = document.getElementById("check-new-spo") as HTMLInputElement;

    expect(component.spos.length).toBeGreaterThan(0);
    expect(chk.disabled).toBeFalse();
  });

  /**
   * Testfall A9.2:UT3 Testen, ob obere Checkbox (SPO) deaktiviert wird falls noch keine SPOs existieren.
   */
   it('should disable the spo-select-checkbox if no spo exists', () => {
    let document = TestBed.inject(DOCUMENT);

    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = [];
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    let chk = document.getElementById("check-new-spo") as HTMLInputElement;

    fixture.detectChanges();

    expect(component.spos.length).toBe(0);
    expect(chk.disabled).toBeTrue();
  });

  /**
   * Testfall A9.2:UT4 Testen, ob untere Checkbox (Datum) und die SPO-FormGroup deaktiviert wird falls die obere Checkbox gesetzt ist.
   */
   it('should disable the endDate-checkbox and the spo-formgroup if spo-select-checkbox is checked and reenable it when unchecked', () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = spos;
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    let document = TestBed.inject(DOCUMENT);
    let chkSPO = document.getElementById("check-new-spo") as HTMLInputElement;
    let chkDate = document.getElementById("check-end-date") as HTMLInputElement;

    fixture.detectChanges();
    expect(component.endDateEnabled).toBeTrue();
    expect(chkDate.disabled).toBeFalse();
    expect(component.spoFormGroup.disabled).toBeFalse();

    chkSPO.click();

    fixture.detectChanges();
    expect(component.endDateEnabled).toBeFalse();
    expect(chkDate.disabled).toBeTrue();
    expect(component.spoFormGroup.disabled).toBeTrue();

    chkSPO.click();

    fixture.detectChanges();
    expect(component.endDateEnabled).toBeTrue();
    expect(chkDate.disabled).toBeFalse();
    expect(component.spoFormGroup.disabled).toBeFalse();
  });

  /**
   * Testfall A9.2:UT4 Testen, ob Zeitraum korrekt umgewandelt wird.
   */
  it('should show the correct output for an given spo-period', () => {
    expect(component.getSpoTimespan(spos[0])).toEqual("ab 2020");
    expect(component.getSpoTimespan(spos[1])).toEqual("von 2014 bis 2020");
    expect(component.getSpoTimespan(spos[2])).toEqual("von 2009 bis 2014");
  });
});
