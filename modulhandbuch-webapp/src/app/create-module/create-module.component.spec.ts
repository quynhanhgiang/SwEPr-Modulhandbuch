import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';

import { CreateModuleComponent } from './create-module.component';

describe('CreateModuleComponent', () => {
  let component: CreateModuleComponent;
  let fixture: ComponentFixture<CreateModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      providers: [
        FormBuilder,
        HttpClient,
        HttpHandler
      ],
      declarations: [
        CreateModuleComponent
      ]
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
  * Testfall A4.4:UT4 Testen, eine Variation bei Länge 1 gelöscht werden kann.
  */
  it("should call alert'Es muss mindestens eine Variation vorhanden sein' when trying to delete one after initialization", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    spyOn(window, "alert");
    
    component.ngOnInit();
    component.deleteVariation(0);
    

    expect(component.variations.length).toBe(1);
    expect(window.alert).toHaveBeenCalledWith("Es muss mindestens eine Variation vorhanden sein");
  });

  /**
  * Testfall A4.4:UT5 Testen, eine Variation hinzugefügt werden kann.
  */
  it("should call alert'Es muss mindestens eine Variation vorhanden sein' when trying to delete one after initialization", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    component.ngOnInit();
    
    component.addVariation();

    expect(component.variations.length).toBe(2);
  });

  /**
  * Testfall A4.4:UT6 Testen, ob reset Form .
  */
   it("should reset 'moduleFormGroup' after calling 'resetForm'", () => {
    fixture = TestBed.createComponent(CreateModuleComponent);
    component = fixture.componentInstance;

    component.ngOnInit();
    
    component.moduleFormGroup.patchValue({
      id:1,
      moduleOwner:"Dr. Prof. Dipl. Med. Dent. Müx Müstermann"
    });

    expect(component.moduleFormGroup.value.id).toBe(1);
    expect(component.moduleFormGroup.value.moduleOwner).toBe("Dr. Prof. Dipl. Med. Dent. Müx Müstermann");

    component.resetForm();

    expect(component.moduleFormGroup.value.id).toBe(null);
    expect(component.moduleFormGroup.value.moduleOwner).toBe(null);
  });

});
