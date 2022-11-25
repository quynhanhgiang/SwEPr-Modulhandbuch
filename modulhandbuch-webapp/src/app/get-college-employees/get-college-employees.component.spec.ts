import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';

import { GetCollegeEmployeesComponent } from './get-college-employees.component';
import { collegeEmployee } from './mock-college-employees';

describe('GetCollegeEmployeesComponent', () => {
  let component: GetCollegeEmployeesComponent;
  let fixture: ComponentFixture<GetCollegeEmployeesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetCollegeEmployeesComponent ],
      providers: [
        HttpClient,
        FormBuilder,
        {provide: ActivatedRoute, useValue: ActivatedRoute},
        HttpHandler
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetCollegeEmployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * Testfall A3.3:UT1 Testen, ob Komponente erzeugt wird.
  */
   it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
  * Testfall A3.3:UT2 Testen, ob beim Laden der Daten die entsprechende Nachricht gesetzt ist
  */
  it("should show 'loading-message' if college-employees are loading", () => {
    expect(component.message).toEqual("Mitarbeiter werden geladen...");
  });

  /**
  * Testfall A3.3:UT3 Testen, ob bei nicht-leeren Datensatz die entsprechende Nachricht gesetzt ist.
  */
  it("should show 'filter-message' if college-employees is not empty", () => {
    fixture = TestBed.createComponent(GetCollegeEmployeesComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
    
    component.ngOnInit();
    
    expect(component.employees.length).toBeGreaterThan(0);
    expect(component.message).toEqual("Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe.");
  });

  /**
  * Testfall A3.3:UT4 Testen, ob bei leerem Datensatz die entsprechende Nachricht gesetzt ist.
  */
   it("should show 'filter-message' if college-employees is empty", () => {
    fixture = TestBed.createComponent(GetCollegeEmployeesComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = [];
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
    
    component.ngOnInit();

    expect(component.employees.length).toBe(0);
    expect(component.message).toEqual("Es wurden noch keine Mitarbeiter angelegt. Zum Anlegen bitte auf 'Neuen Mitarbeiter anlegen' klicken.");
  });
});
