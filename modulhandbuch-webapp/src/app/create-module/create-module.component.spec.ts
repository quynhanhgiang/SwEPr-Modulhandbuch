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
  * Testfall A4.3.A:UT2 Testen, ob Formular nach initialisierung unsichtbar ist.
  */
  it("should set 'display' on false after initialization", () => {
    component.ngOnInit();
    expect(component.display).toEqual(false);
  });


  /**
  * Testfall A4.3.A:UT3 Testen, ob Formular nach betätigen des Knopfes 'Neues Modul erzeugen' sichtbar ist.
  */
   it("should set 'display' on true after call function 'showDialog()'", () => {
    component.ngOnInit();
    component.showDialog();
    expect(component.display).toEqual(true);
  });

  /**
  * Testfall A4.3.A:UT3 Testen, ob Formular nach betätigen des Knopfes 'Neues Modul erzeugen' sichtbar ist.
  */
     it("should set 'display' on true after call function 'showDialog()'", () => {
      component.ngOnInit();
      component.showDialog();
      expect(component.display).toEqual(true);
    });

  /**
  * Testfall A4.4.A:UT3 Testen, eine Variation bei Länge 1 gelöscht werden kann.
  */
  it("should call alert'Es muss mindestens eine Variation vorhanden sein' when trying to delete one after initialization", () => {
    spyOn(window, "alert");
    
    component.ngOnInit();
    
    component.deleteVariation(0);

    expect(component.variations.length).toBe(1);
    expect(window.alert).toHaveBeenCalledWith("Es muss mindestens eine Variation vorhanden sein' when trying to delete one after initialization");
  });

});
