import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ModuleDetailComponent } from './module-detail.component';

describe('ModuleDetailComponent', () => {
  let component: ModuleDetailComponent;
  let fixture: ComponentFixture<ModuleDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModuleDetailComponent ],
      providers: [
        HttpClient,
        FormBuilder,
        {provide: ActivatedRoute, useValue: ActivatedRoute},
        HttpHandler
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModuleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
  * Testfall A6.2.A:UT1 Testen, ob Komponente erzeugt wird.
  */
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
