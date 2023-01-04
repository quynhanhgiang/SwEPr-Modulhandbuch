import {HttpClientTestingModule} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { requirements } from '../edit-module/mock-objects';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';

import { CreateModuleComponent } from './create-module.component';
import { cycles, durations, languages, maternityProtections, moduleManuals, profs, segments, types } from './mock-objects';

describe('CreateModuleComponent', () => {
  let component: CreateModuleComponent;
  let fixture: ComponentFixture<CreateModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        FormBuilder,
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
  * Testfall A4.3:UT7 Testen, ob nach Absenden des Formulars ohne ausgewählte Dozenten eine Fehlermeldung angezeigt wird.
  */
  it("should show alert message'Es muss mindestens ein Dozent zugewiesen werden' when submitting without any selected prof", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    component.moduleFormGroup.patchValue(    {
      id: 0,
      moduleName: "Analysis",
      abbreviation: "Ana",
      variations: [
        {
          manual: {
            id: 1,
            semester: "Wintersemester 2022/2023",
            spo: {
              id: 1,
              link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
              degree: "Bachelor",
              course: "Informatik",
              startDate: "2020-10-01",
              endDate: null
            }
          },
          ects: 7,
          sws: 6,
          workLoad: "<p><span style=\color: rgb(0, 0, 0);\>90 h Präsenz (Seminaristischer Unterricht mit integrierten Übungen)</span></p><p><br></p><p><span style=\color: rgb(0, 0, 0);\>120 h Eigenarbeit (40 h Nachbereitung des Lehrstoffs, 30 h Bearbeitung von Übungsaufgaben, 50 h Prüfungsvorbereitung)</span></p>",
          semester: 1,
          moduleType: "Pflichtfach",
          admissionRequirement: "1",
          segment: "1. Abschnitt"
        }
      ],
      cycle: "Jährlich",
      duration: "Einsemestrig",
      moduleOwner: {
        id: 3,
        firstName: "Ada",
        lastName: "Bäumner",
        title: "Prof. Dr",
        gender: "Sehr geehrte",
        email: "Ada.Baeumner@hs-coburg.de"
      },
      profs: [],
      language: "Deutsch",
      usage: "",
      knowledgeRequirements: "",
      skills: "<p><span style=\color: rgb(0, 0, 0);\>Studierende sollen wesentliche Grundlagen der Analysis bis hin zur Differentialrechnung kennen und anwenden können.</span></p>",
      content: "<p><span style=\color: rgb(0, 0, 0);\>Logik, Mengenlehre, Vollständige Induktion, Kombinatorik, rationale und reelle Zahlen, komplexe Zahlen, Folgen und Grenzwerte, Reihen, Funktionen und Stetigkeit, Differenzierbarkeit, Sätze der Differenzialrechnung Extremwerte, Taylorentwicklung</span></p>",
      examType: "Schriftliche Prüfung (120 Minuten)",
      certificates: "",
      mediaType: "Tafel, Skript",
      literature: "<p><span style=\color: rgb(0, 0, 0);\>T. Arens et al., „Mathematik“, Spektrum, Heidelberg, 2008 </span></p><p><span style=\color: rgb(51, 51, 51);\>G. Teschl, S. Teschl, „Mathematik für Informatiker“, Band 1 und 2, Springer Spektrum Berlin, Heidelberg, 2013</span></p><p><span style=\color: rgb(51, 51, 51);\>W. Struckmann, D. Wätjen, „Mathematik für Informatiker“, Springer Vieweg Berlin, Heidelberg, 2016</span></p><p><span style=\color: rgb(51, 51, 51);\>R. Berghammer, „Mathematik für Informatiker“, Springer Vieweg Wiesbaden, 2014</span></p><p>&nbsp;</p><p><span style=\color: rgb(51, 51, 51);\>E. Weitz, „Konkrete Mathematik (nicht nur) für Informatiker“, Springer Spektrum Wiesbaden, 2018</span></p><p>&nbsp;</p><p><span style=\color: rgb(0, 0, 0);\>O. Forster, „Analysis 1“, Vieweg, Wiesbaden, 2004</span></p>",
      maternityProtection: "Grün"
    });

    spyOn(window, 'alert');

    let btnSaveOpen = document.getElementById("#bt-submit-close") as HTMLButtonElement;
    let submit = new SubmitEvent("submit", { submitter: btnSaveOpen });

    component.onSubmit(submit);

    expect(window.alert).toHaveBeenCalledWith('Es muss mindestens ein Dozent zugewiesen werden');
  });

  /**
  * Testfall A4.3:UT8 Testen, ob das displayProf Objekt nach Absenden des Formulars korrekt umgewandelt wird.
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
      id: 0,
      moduleName: "Analysis",
      abbreviation: "Ana",
      variations: [
        {
          manual: {
            id: 1,
            semester: "Wintersemester 2022/2023",
            spo: {
              id: 1,
              link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
              degree: "Bachelor",
              course: "Informatik",
              startDate: "2020-10-01",
              endDate: null
            }
          },
          ects: 7,
          sws: 6,
          workLoad: "<p><span style=\color: rgb(0, 0, 0);\>90 h Präsenz (Seminaristischer Unterricht mit integrierten Übungen)</span></p><p><br></p><p><span style=\color: rgb(0, 0, 0);\>120 h Eigenarbeit (40 h Nachbereitung des Lehrstoffs, 30 h Bearbeitung von Übungsaufgaben, 50 h Prüfungsvorbereitung)</span></p>",
          semester: 1,
          moduleType: "Pflichtfach",
          admissionRequirement: "1",
          segment: "1. Abschnitt"
        }
      ],
      cycle: "Jährlich",
      duration: "Einsemestrig",
      moduleOwner: {
        id: 3,
        firstName: "Ada",
        lastName: "Bäumner",
        title: "Prof. Dr",
        gender: "Sehr geehrte",
        email: "Ada.Baeumner@hs-coburg.de"
      },
      profs: [
        {
          id: 1,
          name: "Prof. Dr. Dieter Landes",
        },
        {
          id: 2,
          name: "Prof. Volkhard Pfeiffer",
        }
      ],
      language: "Deutsch",
      usage: "",
      knowledgeRequirements: "",
      skills: "<p><span style=\color: rgb(0, 0, 0);\>Studierende sollen wesentliche Grundlagen der Analysis bis hin zur Differentialrechnung kennen und anwenden können.</span></p>",
      content: "<p><span style=\color: rgb(0, 0, 0);\>Logik, Mengenlehre, Vollständige Induktion, Kombinatorik, rationale und reelle Zahlen, komplexe Zahlen, Folgen und Grenzwerte, Reihen, Funktionen und Stetigkeit, Differenzierbarkeit, Sätze der Differenzialrechnung Extremwerte, Taylorentwicklung</span></p>",
      examType: "Schriftliche Prüfung (120 Minuten)",
      certificates: "",
      mediaType: "Tafel, Skript",
      literature: "<p><span style=\color: rgb(0, 0, 0);\>T. Arens et al., „Mathematik“, Spektrum, Heidelberg, 2008 </span></p><p><span style=\color: rgb(51, 51, 51);\>G. Teschl, S. Teschl, „Mathematik für Informatiker“, Band 1 und 2, Springer Spektrum Berlin, Heidelberg, 2013</span></p><p><span style=\color: rgb(51, 51, 51);\>W. Struckmann, D. Wätjen, „Mathematik für Informatiker“, Springer Vieweg Berlin, Heidelberg, 2016</span></p><p><span style=\color: rgb(51, 51, 51);\>R. Berghammer, „Mathematik für Informatiker“, Springer Vieweg Wiesbaden, 2014</span></p><p>&nbsp;</p><p><span style=\color: rgb(51, 51, 51);\>E. Weitz, „Konkrete Mathematik (nicht nur) für Informatiker“, Springer Spektrum Wiesbaden, 2018</span></p><p>&nbsp;</p><p><span style=\color: rgb(0, 0, 0);\>O. Forster, „Analysis 1“, Vieweg, Wiesbaden, 2004</span></p>",
      maternityProtection: "Grün"
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
  * Testfall A4.3:UT9 Testen, ob die Werte für Modulhandbücher korrekt geladen werden.
  */
  it("should get correct values after calling 'updateModuleManual(id,i)'", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
  
    const testTypes: string[] = types;
    spyOn(restApiService, 'getModuleTypes').and.returnValue(of(testTypes));

    const testSegments: string[] = segments;
    spyOn(restApiService, 'getSegments').and.returnValue(of(testSegments));

    const testRequirements: string[] = requirements;
    spyOn(restApiService, 'getRequirements').and.returnValue(of(testRequirements));

    component.updateModuleManual(1,0);

    expect(testRequirements.length).toBe(4);
    expect(testSegments.length).toBe(3)
    expect(testTypes.length).toBe(4);

  });
});
