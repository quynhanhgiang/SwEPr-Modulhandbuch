import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { Module } from '../shared/module';
import { modules } from './mock-modules';

import { ModuleDetailComponent } from './module-detail.component';

describe('ModuleDetailComponent', () => {
  let component: ModuleDetailComponent;
  let fixture: ComponentFixture<ModuleDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModuleDetailComponent ],
      providers:[
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

    fixture = TestBed.createComponent(ModuleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
  * Testfall A6.2.A:UT1 Testen, ob Komponente erzeugt wird.
  */
  it("should create", () => {
    expect(component).toBeTruthy();
  });


  /**
  * Testfall A6.2.A:UT2 Testen, ob 'id' korrekt gelesen wird und das entsprechnde Modul gealden wird.
  */
  it("should get module with id with 1 when calling 'ngOnInit()'", () => {
    fixture = TestBed.createComponent(ModuleDetailComponent);
    component = fixture.componentInstance;
    const restApiService = TestBed.inject(RestApiService);
    const testData: Module = modules[1];
    
    spyOn(restApiService, 'getModule').and.returnValue(of(testData));
   
    component.ngOnInit();

    expect(component.id).toBe(1);
    expect(component.module.id).toBe(1);
    expect(component.rendered).toBe(true);
  });
});