import {HttpClientTestingModule} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';

import { CreateCollegeEmployeeComponent } from './create-college-employee.component';

describe('CreateCollegeEmployeeComponent', () => {
  let component: CreateCollegeEmployeeComponent;
  let fixture: ComponentFixture<CreateCollegeEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCollegeEmployeeComponent ],
      providers: [        
        FormBuilder,
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
  * Testfall A1.3:UT3 Testen, ob Formular nach betätigen des Knopfes 'Neues Modul erzeugen' sichtbar ist.
  */
    it("should set 'display' on true after call function 'showDialog()'", () => {
    component.showDialog();
    expect(component.display).toEqual(true);
  });


  /**
  * Testfall A1.3:UT5 Testen, ob reset Form .
  */
   it("should set values for 'genders' and 'titles' after calling 'ngOnInit()'", () => {
    fixture = TestBed.createComponent(CreateCollegeEmployeeComponent);
    component = fixture.componentInstance;

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
});
