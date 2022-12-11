import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { spos } from '../create-module-manual/mock-spos';
import { RestApiService } from '../services/rest-api.service';

import { EditManualGeneralComponent } from './edit-manual-general.component';
import { availableStatusMock, moduleManualMock, moduleTypesMock, nullStatusMock, requirementsMock, segmentsMock, sposMock } from './mock-data';

describe('EditManualGeneralComponent', () => {
  let component: EditManualGeneralComponent;
  let fixture: ComponentFixture<EditManualGeneralComponent>;
  let restApiService: RestApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    providers: [
      FormBuilder
    ],
    imports: [
      HttpClientTestingModule
    ],
      declarations: [ EditManualGeneralComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualGeneralComponent);
    component = fixture.componentInstance;
    component.id = 1;
    restApiService = TestBed.inject(RestApiService);

    spyOn(restApiService, 'getSPOs').and.returnValue(of(sposMock));
    spyOn(restApiService, 'getModuleManual').and.returnValue(of(moduleManualMock));
    spyOn(restApiService, 'getModulePlanStatus').and.returnValue(of(nullStatusMock));
    spyOn(restApiService, 'getPreliminaryNoteStatus').and.returnValue(of(nullStatusMock));
    spyOn(restApiService, 'getSegments').and.returnValue(of(segmentsMock));
    spyOn(restApiService, 'getModuleTypes').and.returnValue(of(moduleTypesMock));
    spyOn(restApiService, 'getRequirements').and.returnValue(of(requirementsMock));
  });

  /**
   * Testfall A10.3:UT2 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A10.3:UT3 Testen, ob Zeitraum korrekt umgewandelt wird.
   */
  it('should show the correct output for an given spo-period', () => {
    fixture.detectChanges();
    expect(component.getSpoTimespan(spos[0])).toEqual("ab 2020");
    expect(component.getSpoTimespan(spos[1])).toEqual("von 2014 bis 2020");
    expect(component.getSpoTimespan(spos[2])).toEqual("von 2009 bis 2014");
  });

  /**
   * Testfall A10.3:UT4 Testen, ob die uploadModulePlan-Methode erst dann aufgerufen werden kann, wenn eine Datei ausgewählt wurde.
   */
  it('should only call the uploadModulePlan-Method if a file was selected', () => {
    spyOn(restApiService, 'uploadModulePlan').and.returnValue(of(availableStatusMock));

    const inputModulePlan  = fixture.debugElement.query(By.css('#fileinput-moduleplan'));
    const btnModulePlan = fixture.debugElement.query(By.css('#btn-upload-module-plan'));

    btnModulePlan.triggerEventHandler('click', null);

    fixture.detectChanges();
    expect(restApiService.uploadModulePlan).toHaveBeenCalledTimes(0);

    inputModulePlan.nativeElement.dispatchEvent(new InputEvent('change'));
    component.modulePlanFile = new File([''], 'test-file.pdf');
    btnModulePlan.triggerEventHandler('click', null);

    fixture.detectChanges();
    expect(restApiService.uploadModulePlan).toHaveBeenCalled();
  });

  /**
   * Testfall A10.3:UT5 Testen, ob die uploadPreliminaryNote-Methode erst dann aufgerufen werden kann, wenn eine Datei ausgewählt wurde.
   */
  it('should only call the uploadPreliminaryNote-Method if a file was selected', () => {
    spyOn(restApiService, 'uploadPreliminaryNote').and.returnValue(of(availableStatusMock));

    const inputPrelNote  = fixture.debugElement.query(By.css('#fileinput-preliminary-note'));
    const btnPrelNote = fixture.debugElement.query(By.css('#btn-upload-preliminary-note'));

    btnPrelNote.triggerEventHandler('click', null);

    fixture.detectChanges();
    expect(restApiService.uploadPreliminaryNote).toHaveBeenCalledTimes(0);

    inputPrelNote.nativeElement.dispatchEvent(new InputEvent('change'));
    component.preliminaryNoteFile = new File([''], 'test-file.pdf');
    btnPrelNote.triggerEventHandler('click', null);

    fixture.detectChanges();
    expect(restApiService.uploadPreliminaryNote).toHaveBeenCalled();
  });

  /**
   * Testfall A10.3:UT6 Testen, ob das Ändern des Select-Feldes (SPO) auch zu einer Änderung der deaktivierten SPO-Form-Group führt.
   */
  it('should update the spo-formgroup on selection-change', () => {
    fixture.detectChanges();
    const select: HTMLSelectElement = fixture.debugElement.query(By.css('#select-spo')).nativeElement;

    select.value = select.options[2].value;  // <-- select a new value
    select.dispatchEvent(new Event('change'));

    fixture.detectChanges();
    expect(component.selectedSpoIndex).toBe(2);
    expect(component.spoFormGroup.getRawValue()).toEqual(sposMock[2]);
  });

  /**
   * Testfall A10.3:UT7 Testen, ob ein Submit des ersten Formulares (allg. Infos) korrekt abläuft und im Anschluss für 2 Sekunden ein Bestätigungs-Batch angezeigt wird.
   */
  it('should submit the first form and show a success-batch for 2 seconds after successful submission', async () => {
    spyOn(restApiService, 'updateModuleManual').and.returnValue(of(moduleManualMock));
    const form = fixture.debugElement.query(By.css('#form-manual'));

    fixture.detectChanges();
    expect(component.firstFormSuccess).toBeFalse();

    form.triggerEventHandler("ngSubmit", null);

    fixture.detectChanges();
    expect(component.firstFormSuccess).toBeTrue();
    expect(restApiService.updateModuleManual).toHaveBeenCalled();

    await new Promise(f => setTimeout(f, 2000));

    fixture.detectChanges();
    expect(component.firstFormSuccess).toBeFalse();
  });

  /**
   * Testfall A10.3:UT8 Testen, ob ein Submit des dritten Formulares (Zuordnungen) korrekt abläuft und im Anschluss für 2 Sekunden ein Bestätigungs-Batch angezeigt wird.
   */
  it('should submit the third form and show a success-batch for 2 seconds after successful submission', async () => {
    spyOn(restApiService, 'updateSegments').and.returnValue(of(segmentsMock));
    spyOn(restApiService, 'updateModuleTypes').and.returnValue(of(moduleTypesMock));
    spyOn(restApiService, 'updateRequirements').and.returnValue(of(requirementsMock));
    const form = fixture.debugElement.query(By.css('#form-assignments'));

    fixture.detectChanges();
    expect(component.thirdFormSuccess).toBeFalse();

    form.triggerEventHandler("ngSubmit", null);

    fixture.detectChanges();
    expect(component.thirdFormSuccess).toBeTrue();
    expect(restApiService.updateSegments).toHaveBeenCalled();
    expect(restApiService.updateModuleTypes).toHaveBeenCalled();
    expect(restApiService.updateRequirements).toHaveBeenCalled();

    await new Promise(f => setTimeout(f, 2000));

    fixture.detectChanges();
    expect(component.thirdFormSuccess).toBeFalse();
  });
});
