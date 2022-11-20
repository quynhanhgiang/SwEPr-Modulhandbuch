import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import {  ComponentFixture, fakeAsync, TestBed, waitForAsync } from '@angular/core/testing';
import { RestApiService } from '../services/rest-api.service';

import { GetModuleManualsComponent } from './get-module-manuals.component';
import {ModuleManual} from '../shared/module-manual'
import { of } from 'rxjs';
import { moduleManuals } from './mock-module-manuals';

describe('GetModuleManualsComponent', () => {
  let document: Document
  let component: GetModuleManualsComponent;
  let fixture: ComponentFixture<GetModuleManualsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      declarations: [
        GetModuleManualsComponent
      ]
    })
    .compileComponents();

    document = TestBed.inject(DOCUMENT);
    fixture = TestBed.createComponent(GetModuleManualsComponent);
    component = fixture.componentInstance;
  });

  /**
   * Testfall A8.2:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A8.2:UT2 Testen, ob beim Laden der Daten die entsprechende Nachricht gesetzt ist.
   */
  it("should show 'loading-message' if module-manual-list is loading", () => {
    expect(component.emptyMessage).toEqual("Modulhandbücher werden geladen...");
  });

  /**
   * Testfall A8.2:UT3 Testen, ob bei nicht-leeren Datensatz die entsprechende Nachricht gesetzt ist.
   */
   it("should show 'filter-message' if module-manual-list is not empty", () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: ModuleManual[] = moduleManuals;
    spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testData));

    fixture.detectChanges();  //calls ngOnInit

    expect(component.moduleManuals.length).toBeGreaterThan(0);
    expect(component.emptyMessage).toEqual("Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe und stellen Sie sicher, dass lediglich das Studienfach gesucht wurde (z.B. 'Visual Computing').");
  });

  /**
   * Testfall A8.2:UT4 Testen, ob bei leeren Datensatz die entsprechende Nachricht gesetzt ist.
   */
  it("should show 'empty-message' if module-manual-list is empty", () => {
    const restApiService = TestBed.inject(RestApiService);
    const testData: ModuleManual[] = [];
    spyOn(restApiService, 'getModuleManuals').and.returnValue(of(testData));

    fixture.detectChanges();  //calls ngOnInit

    expect(component.moduleManuals.length).toBe(0);
    expect(component.emptyMessage).toEqual("Es wurden noch keine Modulhandbücher angelegt. Zum Anlegen bitte auf 'Neues Modulhandbuch' klicken.");
  });

  /**
   * Testfall A8.2:UT5 Testen, ob Datums-Strings im ISO-Format korrekt umgewandelt werden.
   */
  it("should show the correct german-date for an given iso-date-string", () => {
    expect(component.getGermanDate("2022-11-20")).toEqual("20. November 2022");
    expect(component.getGermanDate("2000-1-1")).toEqual("1. Januar 2000");
    expect(component.getGermanDate("2011-10-05T14:48:00.000Z")).toEqual("5. Oktober 2011");
  });

  /**
   * Testfall A8.2:UT6 Testen, ob die Felder sortOrder und sortKey über die Methode onSortChange korrekt gesetzt werden.
   */
  it("should change the sort-order and -key on select", () => {
    let inputEvent1: HTMLInputElement = document.createElement("input");
    inputEvent1.value = "!spo.startDate"

    component.onSortChange(inputEvent1);
    expect(component.sortOrder).toBe(-1);
    expect(component.sortField).toEqual("spo.startDate");

    let inputEvent2: HTMLInputElement = document.createElement("input");
    inputEvent2.value = "spo.startDate"

    component.onSortChange(inputEvent2);
    expect(component.sortOrder).toBe(1);
    expect(component.sortField).toEqual("spo.startDate");
  });
});
