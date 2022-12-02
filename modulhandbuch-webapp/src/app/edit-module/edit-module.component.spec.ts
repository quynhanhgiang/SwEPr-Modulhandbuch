import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { EditModuleComponent } from './edit-module.component';

describe('EditModuleComponent', () => {
  let component: EditModuleComponent;
  let fixture: ComponentFixture<EditModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditModuleComponent ],
      providers: [
        HttpClient,
        FormBuilder,
        {provide: ActivatedRoute, useValue: ActivatedRoute},
        HttpHandler
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
  * Testfall A6.2.B:UT1 Testen, ob Komponente erzeugt wird.
  */
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
