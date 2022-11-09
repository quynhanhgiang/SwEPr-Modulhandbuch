import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FlatModule } from '../shared/FlatModule';
import { Module }from  '../shared/Module';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  mockURL: String = 'https://495f8ce0-71e5-4622-9d7d-e4c01d0143c1.mock.pstmn.io';
  devURL: String  = 'https://localhost/dev/api';
//prodURL: String = '';

  constructor(private http: HttpClient) {}

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  /**
   * Method for requesting the "{{url}}/modules?flat=true"-api-endpoint per GET.
   * Returns a flat represantation of all modules.
   * @returns list of FlatModule-objects
   */
  getModulesOverview(): Observable<FlatModule[]> {
    return this.http.get<FlatModule[]>(this.devURL + '/modules?flat=true').pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules"-api-endpoint per GET
   * Returns a detailed represantation of all modules.
   * @returns list of Module-objects
   */
  getModules(): Observable<Module[]> {
    return this.http.get<Module[]>(this.devURL + '/modules').pipe(retry(1), catchError(this.handleError));
  }
  
  /**
   * Method for requesting the "{{url}}/modules/{{module_id}}"-api-endpoint per GET.
   * Returns a detailed represantation of one module, specified by its id.
   * @param id the id of the requested module
   * @returns the module object with the corresponding id (if it exists)
   */
  getModule(id: number): Observable<Module> {
    return this.http.get<Module>(this.devURL + '/modules/' + id).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules"-api-endpoint per POST. 
   * Creates a new module.
   * @param module a Module-object to create
   * @returns the created Module-object
   */
  createModule(module: Module): Observable<Module>  {
    return this.http.post<Module>(this.devURL + '/modules', JSON.stringify(module), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules/{{module_id}}"-api-endpoint per PUT. 
   * Updates an existing module, specified by its id.
   * @param module a Module-object to update
   * @returns the updated Module-object
   */
  updateModule(module: Module): Observable<Module>  {
    return this.http.put<Module>(this.devURL + '/modules/' + module.id, JSON.stringify(module), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
