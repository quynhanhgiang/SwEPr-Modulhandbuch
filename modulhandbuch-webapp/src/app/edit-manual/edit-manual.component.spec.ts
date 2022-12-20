import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EditManualComponent } from './edit-manual.component';

describe('EditManualComponent', () => {
  let component: EditManualComponent;
  let fixture: ComponentFixture<EditManualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditManualComponent ],
      providers: [
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

    fixture = TestBed.createComponent(EditManualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * Testfall A10.3:UT1 Testen, ob Komponente erzeugt und die richtige ID aus der URL übernommen wird.
   */
  it('should create and set the url-param-id', () => {
    expect(component).toBeTruthy();
    expect(component.id).toBe(1);
  });
});
