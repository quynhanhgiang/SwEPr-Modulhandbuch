import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { moduleManualMock, moduleTypesMock, requirementsMock, segmentsMock } from '../edit-manual-general/mock-data';
import { RestApiService } from '../services/rest-api.service';

import { EditManualModulesComponent } from './edit-manual-modules.component';
import { assignableModules, assignedModules, invalidManualVariations } from './mock-modules';

describe('EditManualModulesComponent', () => {
  let component: EditManualModulesComponent;
  let fixture: ComponentFixture<EditManualModulesComponent>;
  let restApiService: RestApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        FormBuilder
      ],
      imports: [
        HttpClientTestingModule
      ],
      declarations: [ EditManualModulesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditManualModulesComponent);
    component = fixture.componentInstance;
    component.id = 1;
    restApiService = TestBed.inject(RestApiService);

    spyOn(restApiService, 'getModuleManual').and.returnValue(of(moduleManualMock));
    spyOn(restApiService, 'getModulesAssignableTo').and.returnValue(of(assignableModules));
    spyOn(restApiService, 'getAssignedModules').and.returnValue(of(assignedModules));
    spyOn(restApiService, 'getSegments').and.returnValue(of(segmentsMock));
    spyOn(restApiService, 'getModuleTypes').and.returnValue(of(moduleTypesMock));
    spyOn(restApiService, 'getRequirements').and.returnValue(of(requirementsMock));
  });

  /**
   * Testfall A11.7:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A11.7:UT2 Testen, ob isValidVariation-Methode korrekt überprüft, ob eine Variation valide ist.
   */
  it ('should validate variations correctly', () => {
    fixture.detectChanges();
    expect(component.isValidVariation(invalidManualVariations[0])).toBeFalse();
    expect(component.isValidVariation(assignedModules[0])).toBeTrue();
    expect(component.isValidVariation(invalidManualVariations[1])).toBeFalse();
  });
});
