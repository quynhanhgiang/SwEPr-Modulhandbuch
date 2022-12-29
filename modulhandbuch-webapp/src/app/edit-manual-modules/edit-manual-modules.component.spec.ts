import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { moduleManualMock, moduleTypesMock, requirementsMock, segmentsMock } from '../edit-manual-general/mock-data';
import { RestApiService } from '../services/rest-api.service';

import { EditManualModulesComponent } from './edit-manual-modules.component';
import { assignableModules, assignedModules, invalidManualVariations } from './mock-modules';

describe('EditManualModulesComponent', () => {
  let component: EditManualModulesComponent;
  let fixture: ComponentFixture<EditManualModulesComponent>;
  let restApiService: RestApiService;

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
      declarations: [ EditManualModulesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualModulesComponent);
    component = fixture.componentInstance;
    component.id = 1;
    restApiService = TestBed.inject(RestApiService);

    spyOn(restApiService, 'getModuleManual').and.returnValue(of(moduleManualMock));
    spyOn(restApiService, 'getModulesAssignableTo').and.returnValue(of(assignableModules));
    spyOn(restApiService, 'getAssignedModules').and.returnValue(of(assignedModules));
    spyOn(restApiService, 'getSegments').and.returnValue(of(segmentsMock));
    spyOn(restApiService, 'getModuleTypes').and.returnValue(of(moduleTypesMock));
    spyOn(restApiService, 'getRequirements').and.returnValue(of(requirementsMock));

    fixture.detectChanges();
  });

  /**
   * Testfall A11.7:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A11.7:UT2 Testen, ob isValidVariation-Methode korrekt überprüft, ob eine Variation valide ist.
   */
  it ('should validate variations correctly', () => {
    expect(component.isValidVariation(invalidManualVariations[0])).toBeFalse();
    expect(component.isValidVariation(assignedModules[0])).toBeTrue();
    expect(component.isValidVariation(invalidManualVariations[1])).toBeFalse();
  });

  /**
   * Testfall A11.7:UT3 Testen, ob eine valide Modulvariation beliebig verschoben werden kann.
   */
  it('should be possible to assign or unassign a valid manual-variation', () => {
    const itemTransferEvent: any = {
      items: [
        component.assignedModules[0]
      ]
    }

    let assignedLength = component.assignedModules.length;
    let unassignedLength = component.unassignedModules.length;

    component.unassignedModules.push(itemTransferEvent.items[0]);
    component.assignedModules.splice(component.assignedModules.indexOf(itemTransferEvent.items[0], 0), 1);
    component.unassignModule(itemTransferEvent);

    expect(itemTransferEvent.items[0].isAssigned).toBeFalse();
    expect(component.assignedModules.length).toBe(assignedLength - 1);
    expect(component.unassignedModules.length).toBe(unassignedLength + 1);

    component.assignedModules.push(itemTransferEvent.items[0]);
    component.unassignedModules.splice(component.unassignedModules.indexOf(itemTransferEvent.items[0], 0), 1);
    component.assignModule(itemTransferEvent);

    expect(itemTransferEvent.items[0].isAssigned).toBeTrue();
    expect(component.assignedModules.length).toBe(assignedLength);
    expect(component.unassignedModules.length).toBe(unassignedLength);
  });

  /**
   * Testfall A11.7:UT4 Testen, ob ein nicht-valides Modul (nicht) zugeordnet werden kann.
   */
  it('should not be possible to assign a invalid module', () => {
    const itemTransferEvent: any = {
      items: [
        component.unassignedModules[0]
      ]
    }

    let assignedLength = component.assignedModules.length;
    let unassignedLength = component.unassignedModules.length;

    component.assignedModules.push(itemTransferEvent.items[0]);
    component.unassignedModules.splice(component.unassignedModules.indexOf(itemTransferEvent.items[0], 0), 1);
    component.assignModule(itemTransferEvent);

    expect(itemTransferEvent.items[0].isAssigned).toBeFalse();
    expect(component.assignedModules.length).toBe(assignedLength);
    expect(component.unassignedModules.length).toBe(unassignedLength);
  });

  /**
   * Testfall A11.7:UT5 Testen, ob beim Bearbeiten/Vervollständigen von Modulvariationen die Daten nach dem Speichern korrekt in das zugehörige Objekt übertragen werden.
   */
  it('should save the form-data to the currently selected manual-variation-object, if the form-data is valid', () => {
    const oldObj = structuredClone(component.unassignedModules[0]);

    component.editManualVar(component.unassignedModules[0]);

    fixture.detectChanges();
    expect(component.variationFormGroup.getRawValue()).toEqual(component.unassignedModules[0]);

    const form = fixture.debugElement.query(By.css('#form-variation'));

    component.variationFormGroup.patchValue({
      semester: 8,  //invalid
      segment: segmentsMock[0],
      moduleType: null, //invalid
      sws: 10,
      ects: 20,
      workLoad: "<p>Test123</p>",
      admissionRequirement: null, //invalid
    });

    expect(component.variationFormGroup.valid).toBeFalse();

    form.triggerEventHandler("ngSubmit", null);

    fixture.detectChanges();
    expect(component.variationFormGroup.getRawValue()).not.toEqual(component.unassignedModules[0]);
    expect(component.unassignedModules[0]).toEqual(oldObj);

    component.variationFormGroup.patchValue({
      semester: 7,
      segment: segmentsMock[0],
      moduleType: moduleTypesMock[0],
      sws: 10,
      ects: 20,
      workLoad: "<p>Test123</p>",
      admissionRequirement: null, //invalid
    });

    expect(component.variationFormGroup.valid).toBeFalse();

    form.triggerEventHandler("ngSubmit", null);

    fixture.detectChanges();
    expect(component.variationFormGroup.getRawValue()).not.toEqual(component.unassignedModules[0]);
    expect(component.unassignedModules[0]).toEqual(oldObj);

    component.variationFormGroup.patchValue({
      semester: 7,
      segment: segmentsMock[0],
      moduleType: moduleTypesMock[0],
      sws: 10,
      ects: 20,
      workLoad: "<p>Test123</p>",
      admissionRequirement: requirementsMock[0],
    });

    expect(component.variationFormGroup.valid).toBeTrue();

    form.triggerEventHandler("ngSubmit", null);

    expect(component.unassignedModules[0]).not.toEqual(oldObj);
  });

  /**
   * Testfall A11.7:UT6 Testen, ob beim Bearbeiten bereits vorhandene Daten zu einer Modulvariation korrekt in das Formular geladen werden.
   */
  it('should load the data of the currently selected manual-variation-object into the form', () => {
    component.editManualVar(component.assignedModules[0]);

    expect(component.variationFormGroup.getRawValue()).toEqual(component.assignedModules[0]);
  });

  /**
   * Testfall A11.7:UT7 Testen, ob Click auf "Speichern" die Zuordnungen submittet und im Anschluss für 2 Sekunden ein Bestätigungs-Batch angezeigt wird.
   */
  it('should submit the assignments and show a success-batch for 2 seconds, if button "Speichern" has been clicked', async () => {
    spyOn(restApiService, 'updateAssignedModules').and.returnValue(of(assignedModules));
    const submitBtn = fixture.debugElement.query(By.css('#btn-submit-end'));

    fixture.detectChanges();
    expect(component.submitSuccess).toBeFalse();

    submitBtn.triggerEventHandler("click", null);

    fixture.detectChanges();
    expect(component.submitSuccess).toBeTrue();
    expect(restApiService.updateAssignedModules).toHaveBeenCalled();

    await new Promise(f => setTimeout(f, 2000));

    fixture.detectChanges();
    expect(component.submitSuccess).toBeFalse();
  });

  /**
   * Testfall A11.7:UT8 Testen, ob Click auf "Speichern und Schließen" die Zuordnungen submittet und die Bearbeitungsansicht schließt.
   */
  it('should submit the assignments and change url, if button "Speichern und Schließen" has been clicked', () => {
    spyOn(restApiService, 'updateAssignedModules').and.returnValue(of(assignedModules));
    const submitBtn = fixture.debugElement.query(By.css('#btn-submit-close-end'));

    submitBtn.triggerEventHandler("click", null);

    fixture.detectChanges();

    expect(restApiService.updateAssignedModules).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/module-manuals']);
  });
});
