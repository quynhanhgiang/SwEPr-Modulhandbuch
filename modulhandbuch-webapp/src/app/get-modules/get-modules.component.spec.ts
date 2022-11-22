import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetModulesComponent } from './get-modules.component';

describe('GetModulesComponent', () => {
  let component: GetModulesComponent;
  let fixture: ComponentFixture<GetModulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        HttpClient,
        HttpHandler
      ],
      declarations: [
        GetModulesComponent
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetModulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
