import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FlatModule, Module } from '../shared/module';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  devURL: String = 'https://495f8ce0-71e5-4622-9d7d-e4c01d0143c1.mock.pstmn.io';
  //prodURL: String = '';

  constructor(private http: HttpClient) {}

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };


  getModulesOverview(): Observable<FlatModule[]> {
    return this.http.get<FlatModule[]>(this.devURL + '/modules?flat=true').pipe(retry(1), catchError(this.handleError));
  }

  getModules(): Observable<Module[]> {
    return this.http.get<Module[]>(this.devURL + '/modules').pipe(retry(1), catchError(this.handleError));
  }
  
  getModule(id: number): Observable<Module> {
    return this.http.get<Module>(this.devURL + '/modules/' + id).pipe(retry(1), catchError(this.handleError));
  }

  createModule(module: Module): Observable<Module>  {
    return this.http.post<Module>(this.devURL + '/modules', JSON.stringify(module), this.httpOptions).pipe(retry(1), catchError(this.handleError));
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
