import { DOCUMENT } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

import { CreateModuleManualComponent } from './create-module-manual.component';
import { spos } from './mock-spos';

describe('CreateModuleManualComponent', () => {
  let component: CreateModuleManualComponent;
  let fixture: ComponentFixture<CreateModuleManualComponent>;

  let router = {
    navigate: jasmine.createSpy('navigate')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        FormBuilder,
        { provide: Router, useValue: router }
      ],
      imports: [
        HttpClientTestingModule
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
   * Testfall A9.2:UT1 Testen, ob ein Klick auf den Button "Modulhandbuch erstellen" das Formular sichtbar und ein Klick auf das
   * "X"-Icon das Formular wieder unsichtbar macht.
   */
   it('should show the dialog, if button "Modulhandbuch erstellen" is clicked, and hide it, if icon "X" is clicked', async () => {
    let document = TestBed.inject(DOCUMENT);
    let btn = document.getElementById("btn-create-module-manual") as HTMLButtonElement;

    fixture.detectChanges();
    expect(component.dialogVisible).toBeFalse();

    btn.click();
    expect(component.dialogVisible).toBeTrue();

    component.hideDialog();
    expect(component.dialogVisible).toBeFalse();
  });

  /**
   * Testfall A9.2:UT3 Testen, ob obere Checkbox (SPO) aktiviert wird falls bereits SPOs existieren.
   */
   it('should enable the spo-select-checkbox if spos are already existing ', () => {
    let document = TestBed.inject(DOCUMENT);

    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = spos;
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    component.showDialog();
    fixture.detectChanges();

    let chk = document.getElementById("check-new-spo") as HTMLInputElement;

    expect(component.spos.length).toBeGreaterThan(0);
    expect(chk.disabled).toBeFalse();
  });

  /**
   * Testfall A9.2:UT4 Testen, ob obere Checkbox (SPO) deaktiviert wird falls noch keine SPOs existieren.
   */
   it('should disable the spo-select-checkbox if no spo exists', () => {
    let document = TestBed.inject(DOCUMENT);

    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = [];
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    component.showDialog();
    fixture.detectChanges();

    let chk = document.getElementById("check-new-spo") as HTMLInputElement;

    expect(component.spos.length).toBe(0);
    expect(chk.disabled).toBeTrue();
  });

  /**
   * Testfall A9.2:UT5 Testen, ob untere Checkbox (Datum) und die SPO-FormGroup deaktiviert wird falls die obere Checkbox gesetzt ist.
   * Beim Anhaken der Checkbox sollte die SPO-FormGroup mit den Daten der ersten SPO gefüllt werden. Beim Abhaken sollten alle Felder zurückgesetzt werden.
   */
   it('should disable the endDate-checkbox and the spo-formgroup if spo-select-checkbox is checked and reenable it when unchecked', () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: Spo[] = spos;
    spyOn(restApiService, 'getSPOs').and.returnValue(of(testData));

    let document = TestBed.inject(DOCUMENT);

    component.showDialog();
    fixture.detectChanges();

    let chkSPO = document.getElementById("check-new-spo") as HTMLInputElement;
    let chkDate = document.getElementById("check-end-date") as HTMLInputElement;

    expect(chkDate.disabled).toBeFalse();
    expect(component.spoFormGroup.disabled).toBeFalse();
    expect(component.spoFormGroup.controls["link"].value).toEqual("");

    chkSPO.click();

    fixture.detectChanges();
    expect(chkDate.disabled).toBeTrue();
    expect(component.spoFormGroup.disabled).toBeTrue();
    expect(component.spoFormGroup.controls["link"].value).toEqual(spos[0].link);

    chkSPO.click();

    fixture.detectChanges();
    expect(chkDate.disabled).toBeFalse();
    expect(component.spoFormGroup.disabled).toBeFalse();
    expect(component.spoFormGroup.controls["link"].value).toEqual("");
  });

  /**
   * Testfall A9.2:UT6 Testen, ob Zeitraum korrekt umgewandelt wird.
   */
  it('should show the correct output for an given spo-period', () => {
    expect(component.getSpoTimespan(spos[0])).toEqual("ab 2020");
    expect(component.getSpoTimespan(spos[1])).toEqual("von 2014 bis 2020");
    expect(component.getSpoTimespan(spos[2])).toEqual("von 2009 bis 2014");
  });

  /**
   * Testfall A9.2:UT4 Testen, ob End-Datumsauswahl deaktiviert wird, falls untere Checkbox gesetzt ist.
   */
   it('should disable the enddate-picker if enddate-checkbox is unset and vice-versa', () => {
    let document = TestBed.inject(DOCUMENT);

    component.showDialog();
    fixture.detectChanges();

    let chk = document.getElementById("check-end-date") as HTMLInputElement;

    chk.click();

    fixture.detectChanges();
    expect(component.endDateEnabled).toBeFalse();
    expect(component.spoFormGroup.controls["endDate"].value).toBeNull();

    chk.click();

    fixture.detectChanges();
    expect(component.endDateEnabled).toBeTrue();
    expect(component.spoFormGroup.controls["endDate"].value).toEqual( new Date().toISOString().split('T')[0]);
   });

  /**
   * Testfall A9.2:UT8 Testen, ob Submit eines invaliden Formulars die Methode onSubmit triggert.
   */
  it('should not submit the form, if it is invalid', () => {
    let document = TestBed.inject(DOCUMENT);

    spyOn(component, "onSubmit");

    component.showDialog();
    fixture.detectChanges();

    let btnSave = document.getElementById("bt-submit-close") as HTMLButtonElement;
    let btnSaveOpen = document.getElementById("bt-submit-open") as HTMLButtonElement;
    let btnSaveReset = document.getElementById("bt-submit-new") as HTMLButtonElement;

    btnSave.click();
    btnSaveOpen.click();
    btnSaveReset.click();

    fixture.detectChanges();

    expect(component.onSubmit).toHaveBeenCalledTimes(0);
  });

  /**
   * Testfall A9.2:UT9 Testen, ob Click auf "Speichern" ein valides Formular submittet und den Dialog schließt.
   */
  it('should submit the form and close the dialog, if the form is valid and button "Speichern" has been clicked', async () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: ModuleManual = {
      id: 10,
      semester: "Sommersemester 2023",
      spo: spos[0]
    };

    spyOn(restApiService, 'createModuleManual').and.returnValue(of(testData));

    let document = TestBed.inject(DOCUMENT);

    component.showDialog();
    fixture.detectChanges();

    let btnSave = document.getElementById("bt-submit-close") as HTMLButtonElement;
    let submit = new SubmitEvent("submit", { submitter: btnSave });

    component.spoFormGroup.patchValue(spos[0]);

    component.manualFormGroup.patchValue({
      semesterType: "Sommersemester",
      semesterYears: "2023",
      spoGroup: component.spoFormGroup
    });

    component.onSubmit(submit);
    fixture.detectChanges();

    expect(component.manualFormGroup.getRawValue()).toEqual({
      semesterType: "Sommersemester",
      semesterYears: "2023",
      spoGroup: spos[0]
    });
    expect(restApiService.createModuleManual).toHaveBeenCalled();
    expect(component.dialogVisible).toBeFalse();
  });

  /**
   * Testfall A9.2:UT10 Testen, ob Click auf "Speichern und Öffnen" ein valides Formular submittet und die URL ändert.
   */
  it('should submit the form and change url, if the form is valid and button "Speichern und Öffnen" has been clicked', () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: ModuleManual = {
      id: 10,
      semester: "Sommersemester 2023",
      spo: spos[0]
    };

    spyOn(restApiService, 'createModuleManual').and.returnValue(of(testData));

    let document = TestBed.inject(DOCUMENT);

    component.showDialog();
    fixture.detectChanges();

    let btnSaveOpen = document.getElementById("bt-submit-open") as HTMLButtonElement;
    let submit = new SubmitEvent("submit", { submitter: btnSaveOpen });

    component.spoFormGroup.patchValue(spos[0]);

    component.manualFormGroup.patchValue({
      semesterType: "Sommersemester",
      semesterYears: "2023",
      spoGroup: component.spoFormGroup
    });

    component.onSubmit(submit);
    fixture.detectChanges();

    expect(component.manualFormGroup.getRawValue()).toEqual({
      semesterType: "Sommersemester",
      semesterYears: "2023",
      spoGroup: spos[0]
    });
    expect(restApiService.createModuleManual).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/module-manual-detail-view', 10]);
  });

  /**
   * Testfall A9.2:UT11 Testen, ob Click auf "Speichern und Zurücksetzen" ein valides Formular submittet und zurücksetzt.
   */
  it('should submit and reset the form, if the form is valid and button "Speichern und Zurücksetzen" has been clicked', () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: ModuleManual = {
      id: 10,
      semester: "Sommersemester 2023",
      spo: spos[0]
    };
    spyOn(restApiService, 'createModuleManual').and.returnValue(of(testData));

    let document = TestBed.inject(DOCUMENT);

    component.showDialog();
    fixture.detectChanges();

    let btnSaveReset = document.getElementById("bt-submit-new") as HTMLButtonElement;
    let submit = new SubmitEvent("submit", { submitter: btnSaveReset });
    let chkSPO = document.getElementById("check-new-spo") as HTMLInputElement;

    chkSPO.click();

    component.manualFormGroup.patchValue({
      semesterType: "Sommersemester",
      semesterYears: "2023",
      spoGroup: component.spoFormGroup
    });

    component.onSubmit(submit);
    fixture.detectChanges();

    expect(restApiService.createModuleManual).toHaveBeenCalled();
    expect(component.spoFormGroup.controls["link"].value).toEqual("");
  });
});
