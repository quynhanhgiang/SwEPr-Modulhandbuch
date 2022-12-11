import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EditModuleComponent } from './edit-module.component';
import { moduleManuals, profs, cycles, durations, languages, maternityProtections } from './mock-objects';

describe('EditModuleComponent', () => {
  let component: EditModuleComponent;
  let fixture: ComponentFixture<EditModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditModuleComponent ],
      providers:[
        FormBuilder,
        HttpClient,
        HttpHandler,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({
              id: 1,
            }),
          },
        },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditModuleComponent);
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
});
