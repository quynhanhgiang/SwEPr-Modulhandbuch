import {HttpClientTestingModule} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { Assignment } from '../shared/Assignments';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';

import { CreateModuleComponent } from './create-module.component';
import { cycles, durations, languages, maternityProtections, moduleManuals, profs, requirements, segments, types } from './mock-objects';

describe('CreateModuleComponent', () => {
  let component: CreateModuleComponent;
  let fixture: ComponentFixture<CreateModuleComponent>;
  
  const fakeActivatedRoute = {
    snapshot: { data: {  } }
  } as ActivatedRoute;

  let router = {
    navigate: jasmine.createSpy('navigate')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        FormBuilder,
        {
         provide: ActivatedRoute, useValue: fakeActivatedRoute
        },
        { provide: Router, useValue: router }
      ],
      declarations: [
        CreateModuleComponent
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

    /**
   * Testfall A4.3:UT1 Testen, ob Komponente erzeugt wird.
   */
  it("should create", () => {
    expect(component).toBeTruthy();
  });

  /**
  * Testfall A4.3:UT2 Testen, ob Formular nach initialisierung unsichtbar ist.
  */
  it("should set 'display' on false after initialization", () => {
    expect(component.display).toEqual(false);
  });

  /**
  * Testfall A4.3:UT3 Testen, ob Formular nach betätigen des Knopfes 'Neues Modul erzeugen' sichtbar ist.
  */
   it("should set 'display' on true after call function 'showDialog()'", () => {
    component.showDialog();
    expect(component.display).toEqual(true);
  });

  /**
  * Testfall A4.3:UT4 Testen, ob Variationen hinzugefügt und gelöscht werden können
  */
  it("should add variation after calling 'addVariation()'and delete variation after calling 'deleteVAriation(i)", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    expect(component.variations.length).toBe(0);

    component.addVariation();
    component.addVariation();

    expect(component.variations.length).toBe(2);

    component.deleteVariation(component.variations.length-1);

    expect(component.variations.length).toBe(1);
  });

  /**
  * Testfall A4.3:UT5 Testen, ob beim Aufruf von HideDialog alle erforderlichen Werte zurückgesetzt werden.
  */
    it("should reset the formGroup and delete all variations and add an new one after calling 'hideDialog()'", () => {
      fixture = TestBed.createComponent(CreateModuleComponent);
      component = fixture.componentInstance;
      component.addVariation();
      component.addVariation();

      expect(component.variations.length).toBe(2);

      component.hideDialog();

      expect(component.variations.length).toBe(1);
      expect(component.display).toBe(false);
      expect(component.selectedProfs.length).toBe(0);
      expect(component.moduleFormGroup.value.moduleName).toEqual(null)
    });

  /**
  * Testfall A4.3:UT6 Testen, ob nach Aufruf von ngOnInit() alles korrekt initialisiert wurde.
  */
  it("should initialize all values correct after calling 'ngOnInit()'", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);

    const testModuleManuals: ModuleManual[] = moduleManuals;
    spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testModuleManuals));

    const testEmplyees: CollegeEmployee[] = profs;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testEmplyees));

    const testCycles:string[] = cycles;
    spyOn(restApiService, 'getCycles').and.returnValue(of(testCycles));

    const testDurations: string[] = durations;
    spyOn(restApiService, 'getDurations').and.returnValue(of(testDurations));

    const testLanguages: string[] = languages;
    spyOn(restApiService, 'getLanguages').and.returnValue(of(testLanguages));

    const testMaternityProtections: string[] = maternityProtections;
    spyOn(restApiService, 'getMaternityProtections').and.returnValue(of(testMaternityProtections));

    component.ngOnInit();

    expect(testModuleManuals).toEqual(component.moduleManuals);
    expect(testEmplyees).toEqual(component.profs);
    expect(testCycles.length).toBe(component.cycles.length);
    expect(testDurations.length).toBe(component.durations.length);
    expect(testLanguages.length).toBe(component.languages.length);
    expect(testMaternityProtections.length).toBe(component.maternityProtections.length);
    expect(component.variations.length).toBe(1);
    expect(component.displayProfs.length).toBe(component.profs.length);
  });



  /**
  * Testfall A4.3:UT7 Testen, ob das displayProf Objekt nach Absenden des Formulars korrekt umgewandelt wird.
  */
  it("should convert displayEmployee object to CollegeEmployee after submitting", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);

    const testModuleManuals: ModuleManual[] = moduleManuals;
    spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testModuleManuals));

    const testEmplyees: CollegeEmployee[] = profs;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testEmplyees));

    const testCycles:string[] = cycles;
    spyOn(restApiService, 'getCycles').and.returnValue(of(testCycles));

    const testDurations: string[] = durations;
    spyOn(restApiService, 'getDurations').and.returnValue(of(testDurations));

    const testLanguages: string[] = languages;
    spyOn(restApiService, 'getLanguages').and.returnValue(of(testLanguages));

    const testMaternityProtections: string[] = maternityProtections;
    spyOn(restApiService, 'getMaternityProtections').and.returnValue(of(testMaternityProtections));

    component.ngOnInit();

    component.moduleFormGroup.patchValue(    {
      profs: [
        {
          id: 1,
          name: "Prof. Dr. Dieter Landes",
        },
        {
          id: 2,
          name: "Prof. Volkhard Pfeiffer",
        }
      ]
    });

    expect(component.moduleFormGroup.value.profs[0].id).toBe(1);
    expect(component.moduleFormGroup.value.profs[1].id).toBe(2);


    let btnSaveOpen = document.getElementById("btn-submit-new") as HTMLButtonElement;
    let submit = new SubmitEvent("submit", { submitter: btnSaveOpen });

    const testNewModule: Module = component.moduleFormGroup.value;
    spyOn(restApiService, 'createModule').and.returnValue(of(testNewModule));

    component.onSubmit(submit);

    expect(component.newModule.profs.length).toBe(2)
    expect(component.newModule.profs[0].id).toBe(1);
    expect(component.newModule.profs[1].id).toBe(2);
    expect(component.display).toBe(true);
  });

    /**
  * Testfall A4.3:UT7 Testen, Der Nutzer nach absenden auf eine neue Seite weitergeleitet wird
  */
    it("should redirect to 'module-detail/id' after submitting", () => {
      fixture = TestBed.createComponent(CreateModuleComponent);
      component = fixture.componentInstance;
  
      const restApiService = TestBed.inject(RestApiService);
  
      const testModuleManuals: ModuleManual[] = moduleManuals;
      spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testModuleManuals));
  
      const testEmplyees: CollegeEmployee[] = profs;
      spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testEmplyees));
  
      const testCycles:string[] = cycles;
      spyOn(restApiService, 'getCycles').and.returnValue(of(testCycles));
  
      const testDurations: string[] = durations;
      spyOn(restApiService, 'getDurations').and.returnValue(of(testDurations));
  
      const testLanguages: string[] = languages;
      spyOn(restApiService, 'getLanguages').and.returnValue(of(testLanguages));
  
      const testMaternityProtections: string[] = maternityProtections;
      spyOn(restApiService, 'getMaternityProtections').and.returnValue(of(testMaternityProtections));
  
      component.ngOnInit();
  
      component.moduleFormGroup.patchValue(    {
        id: 0,
        profs: [],
      });
  
      let btnSaveOpen = document.getElementById("btn-submit-open") as HTMLButtonElement;
      let submit = new SubmitEvent("submit", { submitter: btnSaveOpen });
      
      fixture.detectChanges();

      component.onSubmit(submit);
    });

  /**
  * Testfall A4.3:UT9 Testen, ob die Werte für Modulhandbücher korrekt geladen werden.
  */
  it("should get correct values after calling 'updateModuleManual(i)'", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);

    const testTypes: Assignment[] = types;
    spyOn(restApiService, 'getModuleTypes').and.returnValue(of(testTypes));

    const testSegments: Assignment[] = segments;
    spyOn(restApiService, 'getSegments').and.returnValue(of(testSegments));

    const testRequirements: Assignment[] = requirements;
    spyOn(restApiService, 'getRequirements').and.returnValue(of(testRequirements));

    const testModuleManuals: ModuleManual[] = moduleManuals;
    spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testModuleManuals));
    
    const testEmplyees: CollegeEmployee[] = profs;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testEmplyees));

    const testCycles:string[] = cycles;
    spyOn(restApiService, 'getCycles').and.returnValue(of(testCycles));

    const testDurations: string[] = durations;
    spyOn(restApiService, 'getDurations').and.returnValue(of(testDurations));

    const testLanguages: string[] = languages;
    spyOn(restApiService, 'getLanguages').and.returnValue(of(testLanguages));

    const testMaternityProtections: string[] = maternityProtections;
    spyOn(restApiService, 'getMaternityProtections').and.returnValue(of(testMaternityProtections));

    component.ngOnInit();

    component.moduleFormGroup.patchValue(    {
      variations: [
        {
          manual: {
            id: 1,
          },
        }
      ]
    });
    component.updateModuleManual(0);

    expect(testRequirements.length).toBe(4);
    expect(testSegments.length).toBe(3)
    expect(testTypes.length).toBe(4);

  });
});
