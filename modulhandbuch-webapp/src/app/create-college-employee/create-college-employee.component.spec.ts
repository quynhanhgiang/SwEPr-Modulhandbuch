import {HttpClientTestingModule} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';

import { CreateCollegeEmployeeComponent } from './create-college-employee.component';
import { collegeEmployee } from './mock-college-employees';

describe('CreateCollegeEmployeeComponent', () => {
  let component: CreateCollegeEmployeeComponent;
  let fixture: ComponentFixture<CreateCollegeEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCollegeEmployeeComponent ],
      providers: [        
        FormBuilder,
        ConfirmationService
      ],
      imports: [
        HttpClientTestingModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * A1.3 UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
  * Testfall A1.3:UT2 Testen, ob Formular nach initialisierung unsichtbar ist.
  */
  it("should set 'display' on false after initialization", () => {
    expect(component.display).toEqual(false);
  });
  
  /**
  * Testfall A1.3:UT3 Testen, ob Formular nach betätigen des Knopfes 'Mitarbeiter anlegen' sichtbar ist.
  */
    it("should set 'display' on true after call function 'showDialog()'", () => {
    component.showDialog();
    expect(component.display).toEqual(true);
  });


  /**
  * Testfall A1.3:UT4 Testen, ob nach Initalisierung der Komponente die richtigen Werte geladen wurden.
  */
   it("should set values for 'genders' and 'titles' after calling 'ngOnInit()'", () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));

    component.ngOnInit();

    expect(component.titles).toEqual(["Prof.","Dr.", "Dipl."]);
    expect(component.genders).toEqual(["Herr","Frau", "Divers"]);

  });

  /**
  * Testfall A1.3:UT5 Testen, ob die function reset Form funktioniert.
  */
  it("should reset 'employeeFormGroup' after calling 'resetForm'", () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;

    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));

    component.ngOnInit();
    
    component.employeeFormGroup.patchValue({
      id:1,
      title:"Dr. Prof. Dipl. Med. Dent."
    });

    expect(component.employeeFormGroup.value.id).toBe(1);
    expect(component.employeeFormGroup.value.title).toBe("Dr. Prof. Dipl. Med. Dent.");

    component.resetForm();

    expect(component.employeeFormGroup.value.id).toBe(null);
    expect(component.employeeFormGroup.value.title).toBe(null);
  });

  /**
  * Testfall A1.3:UT6 Testen ob, 'Diverse' nach aufruf der Funktion 'onSubmit' zu '' wird.
  */
  it("should set gender to ''after calling 'onSubmit'", () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));

    component.ngOnInit();

    component.employeeFormGroup.patchValue({
      id:null,
      title:["Dr.", "Prof.", "Dipl.", "Med.", "Dent."],
      firstName:"Müx",
      lastName:"Müstermann",
      gender:"Divers",
      email:"müxmüstermann@exmaple.com"
    });

    spyOn(component, "resetForm");

    component.onSubmit();

    expect(component.newCollegeEmployee.gender).toBe("");
    expect(component.newCollegeEmployee.title).toBe("Dr. Prof. Dipl. Med. Dent.");
    expect(component.resetForm).toHaveBeenCalled();
  });


  /**
  * Testfall A1.3:UT7 Testen ob, Titel 'null' nach aufruf der Funktion 'onSubmit' zu '""' wird.
  */
    it("should set title to ''after calling 'onSubmit' with a duplicate amail", () => {
      fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
      component = fixture.componentInstance;
      
      const restApiService = TestBed.inject(RestApiService);
      const testData: CollegeEmployee[] = collegeEmployee;
      spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
  
      component.ngOnInit();
  
      component.employeeFormGroup.patchValue({
        id:null,
        title:null,
        firstName:"Müx",
        lastName:"Müstermann",
        gender:"Divers",
        email:"müxmüstermann@exmaple.com"
      });
  
      component.onSubmit();
  
      expect(component.newCollegeEmployee.title).toBe("");
    });

  /**
  * Testfall A1.3:UT8 Testen ob das Bestätigungsfeld geöffnet wird, wenn versucht wird einen Nutzer mit einem bereits vorhandenne Namne anzulegen.
  */
  it("should call 'confirm()' after calling'submit' with a duplicate Name" , () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
    component.ngOnInit();

    component.employeeFormGroup.patchValue({
      firstName:testData[0].firstName,
      lastName:testData[0].lastName
    });

    spyOn(component, "confirm");

    component.onSubmit();
    expect(component.confirm).toHaveBeenCalled();

  });

  
  /**
  * Testfall A1.3:UT9 Testen ob eine Warnung angezeigt wird, wenn versucht wird einen Nutzer mit einer bereits vorhandenne Email anzulegen.
  */
  it("should call 'alert' with:'Ein Nutzer mit dieser Email-Adresse ist bereits angelegt' after calling'onSubmit()' with a duplicate email" , () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;
    
    const restApiService = TestBed.inject(RestApiService);
    const testData: CollegeEmployee[] = collegeEmployee;
    
    spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
    spyOn(window,'alert');

    component.ngOnInit();

    component.employeeFormGroup.patchValue({
      email:testData[0].email
    });

    component.onSubmit();
    expect(window.alert).toHaveBeenCalledWith("Ein Nutzer mit dieser Email-Adresse ist bereits angelegt");

  });

  /**
  * Testfall A1.3:UT10 Testen ob der Nutzer angelegt wird, wenn bei dem Bestätigungsfeld auf 'ja' gedrückt wird.
  */
    it("should create newEmployee after calling 'accept()'" , () => {
      fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
      component = fixture.componentInstance;
      
      const restApiService = TestBed.inject(RestApiService);

      const testData: CollegeEmployee[] = collegeEmployee;
      spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
      component.ngOnInit();
  
      component.employeeFormGroup.patchValue({
        firstName:testData[0].firstName,
        lastName:testData[0].lastName
      });
  
      spyOn(component, "confirm");
  
      expect(component.doubleName).toBe(false);
      component.onSubmit();

      const testNewEmployee:CollegeEmployee = component.employeeFormGroup.value;
      spyOn(restApiService, 'createCollegeEmployee').and.returnValue(of(testNewEmployee));

      component.accept();

      expect(component.confirm).toHaveBeenCalled();
      expect(restApiService.createCollegeEmployee).toHaveBeenCalled();
  
    });


  /**
  * Testfall A1.3:UT11 Testen ob der das Formular zurückgesetzt wird und geöfnnet bleibt, wenn bei dem Bestätigungsfeld auf 'nein' gedrückt wird.
  */
      it("should reset employeeFormGroup after calling 'reject()'" , () => {
        fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
        component = fixture.componentInstance;
        
        const restApiService = TestBed.inject(RestApiService);
  
        const testData: CollegeEmployee[] = collegeEmployee;
        spyOn(restApiService, 'getCollegeEmployees').and.returnValue(of(testData));
        
        component.showDialog();
        component.ngOnInit();
    
        component.employeeFormGroup.patchValue({
          firstName:testData[0].firstName,
          lastName:testData[0].lastName
        });
    
        spyOn(component, "confirm");
        spyOn(component.employeeFormGroup,'reset');
    
        expect(component.doubleName).toBe(false);

        component.onSubmit();

        expect(component.confirm).toHaveBeenCalled();
  
        component.reject();
  
        expect(component.employeeFormGroup.reset).toHaveBeenCalled();
        expect(component.title).toBe("");
        expect(component.display).toBe(true);
      });

});
