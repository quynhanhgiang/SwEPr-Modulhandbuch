import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RestApiService } from '../services/rest-api.service';
import { FlatModule } from '../shared/FlatModule';
import { of } from 'rxjs';

import { GetModulesComponent } from './get-modules.component';
import { flatModules } from './mock-flat-modules';

describe('GetModulesComponent', () => {
  let component: GetModulesComponent;
  let fixture: ComponentFixture<GetModulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        HttpClient,
        HttpHandler
      ],
      declarations: [
        GetModulesComponent
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetModulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * Testfall A5.4:UT1 Testen, ob Komponente erzeugt wird.
  */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
  * Testfall A5.4:UT2 Testen, ob beim Laden der Daten die entsprechende Nachricht gesetzt ist
  */
  it("should show 'loading-message' if module-manual-list is loading", () => {
    expect(component.message).toEqual("Module werden geladen...");
  });

  /**
  * Testfall A5.2:UT3 Testen, ob bei nicht-leeren Datensatz die entsprechende Nachricht gesetzt ist.
  */
  it("should show 'filter-message' if module-manual-list is not empty", () => {
    fixture = TestBed.createComponent(GetModulesComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);
    const testData: FlatModule[] = flatModules;
    spyOn(restApiService, 'getModulesOverview').and.returnValue(of(testData));
    
    component.ngOnInit();
    
    expect(component.modules.length).toBeGreaterThan(0);
    expect(component.message).toEqual("Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe.");
  });

  /**
  * Testfall A5.3:UT4 Testen, ob bei leerem Datensatz die entsprechende Nachricht gesetzt ist.
  */
   it("should show 'filter-message' if module-manual-list is empty", () => {
    fixture = TestBed.createComponent(GetModulesComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
    const testData: FlatModule[] = [];
    spyOn(restApiService, 'getModulesOverview').and.returnValue(of(testData));
    
    component.ngOnInit();

    expect(component.modules.length).toBe(0);
    expect(component.message).toEqual("Es wurden noch keine Module angelegt. Zum Anlegen bitte auf 'Neues Modul erstellen' klicken.");
  });


});
