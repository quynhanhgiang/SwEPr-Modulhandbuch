import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { RestApiService } from './rest-api.service';

describe('RestApiService', () => {
  let service: RestApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ]
    });
    service = TestBed.inject(RestApiService);
  });

  /**
   * S1:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
