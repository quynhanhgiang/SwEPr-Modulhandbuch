import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FlatModule } from '../shared/FlatModule';
import { Module }from  '../shared/module';
import { ModuleManual } from '../shared/module-manual';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Spo } from '../shared/spo';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Assignment } from '../shared/AssignmentInterfaces';
import { FileStatus } from '../shared/FileStatus';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  apiURL: String = environment.apiURL;
  prod: boolean = environment.production;

  constructor(private http: HttpClient) {}

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  // ########## Modules-API ##########

  /**
   * Method for requesting the "{{url}}/modules?flat=true"-api-endpoint per GET.
   * Returns a flat represantation of all modules.
   * @returns list of FlatModule-objects
   */
  getModulesOverview(): Observable<FlatModule[]> {
    return this.http.get<FlatModule[]>( this.apiURL + (this.prod ?'/modules?flat=true' : '/flatModules')).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules"-api-endpoint per GET
   * Returns a detailed represantation of all modules.
   * @returns list of Module-objects
   */
  getModules(): Observable<Module[]> {
    return this.http.get<Module[]>(this.apiURL + '/modules').pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules/{{module_id}}"-api-endpoint per GET.
   * Returns a detailed represantation of one module, specified by its id.
   * @param id the id of the requested module
   * @returns the Module-object with the corresponding id (if it exists)
   */
  getModule(id: number): Observable<Module> {
    return this.http.get<Module>(this.apiURL + '/modules/' + id).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules"-api-endpoint per POST.
   * Creates a new module.
   * @param module a Module-object to create
   * @returns the created Module-object
   */
  createModule(module: Module): Observable<Module>  {
    return this.http.post<Module>(this.apiURL + '/modules', JSON.stringify(module), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/modules/{{module_id}}"-api-endpoint per PUT.
   * Updates an existing module, specified by its id.
   * @param module a Module-object to update
   * @returns the updated Module-object
   */
  updateModule(module: Module): Observable<Module>  {
    return this.http.put<Module>(this.apiURL + '/modules/' + module.id, JSON.stringify(module), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }


  // ########## Module-Manuals-API ##########

  /**
   * Method for requesting the "{{url}}/module-manuals"-api-endpoint per GET
   * Returns a list of all module-manuals.
   * @returns list of ModuleManual-objects
   */
  getModuleManuals(): Observable<ModuleManual[]> {
    return this.http.get<ModuleManual[]>(this.apiURL + '/module-manuals').pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/module-manuals/{{module_manual_id}}"-api-endpoint per GET.
   * Returns a represantation of one module-manual, specified by its id.
   * @param id the id of the requested module-manual
   * @returns the ModuleManual-object with the corresponding id (if it exists)
   */
  getModuleManual(id: number): Observable<ModuleManual> {
    return this.http.get<ModuleManual>(this.apiURL + '/module-manuals/' + id).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/module-manuals"-api-endpoint per POST.
   * Creates a new module-manual.
   * @param moduleManual a ModuleManual-object to create
   * @returns the created ModuleManual-object
   */
  createModuleManual(moduleManual: ModuleManual): Observable<ModuleManual>  {
    return this.http.post<ModuleManual>(this.apiURL + '/module-manuals', JSON.stringify(moduleManual), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/module-manuals/{{module_manual_id}}"-api-endpoint per PUT.
   * Updates an existing module-manual, specified by its id.
   * @param moduleManual a ModuleManual-object to update
   * @returns the updated ModuleManual-object
   */
  updateModuleManual(moduleManual: ModuleManual): Observable<ModuleManual>  {
    return this.http.put<ModuleManual>(this.apiURL + '/module-manuals/' + moduleManual.id, JSON.stringify(moduleManual), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }


  // ########## Module-Manuals-API: Documents ##########

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/first-page"-api-endpoint per GET.
   * Returns the status of the first-page-file for an given module-manual.
   * @param manualID ID of the target-manual
   * @returns file-status as FileStatus-Object
   */
   getFirstPageStatus(manualID: number): Observable<FileStatus> {
    return this.http.get<FileStatus>(this.apiURL + '/module-manuals/' + manualID + "/first-page").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/first-page"-api-endpoint per PUT.
   * Used to set / upload a first-page for the module-manual with the given ID.
   * @param manualID ID of the target-manual
   * @param file the first-page as .png, .jpeg or .pdf file
   * @returns ?
   */
  uploadFirstPage(manualID: number, file: File): Observable<FileStatus> {
    const formData: FormData = new FormData();
    formData.append('firstPageFile', file, file.name);

    return this.http.post<FileStatus>(this.apiURL + "/module-manuals/" + manualID + "/first-page", formData).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/module-plan"-api-endpoint per GET.
   * Returns the status of the module-plan-file for an given module-manual.
   * @param manualID ID of the target-manual
   * @returns file-status as FileStatus-Object
   */
   getModulePlanStatus(manualID: number): Observable<FileStatus> {
    return this.http.get<FileStatus>(this.apiURL + '/module-manuals/' + manualID + "/module-plan").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/module-plan"-api-endpoint per PUT.
   * Used to set / upload a module-plan for the module-manual with the given ID.
   * @param manualID ID of the target-manual
   * @param file the module-manual as .png, .jpeg or .pdf file
   * @returns ?
   */
  uploadModulePlan(manualID: number, file: File): Observable<FileStatus> {
    const formData: FormData = new FormData();
    formData.append('modulePlanFile', file, file.name);

    return this.http.post<FileStatus>(this.apiURL + "/module-manuals/" + manualID + "/module-plan", formData).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/preliminary-note"-api-endpoint per GET.
   * Returns the status of the preliminary-note-file for an given module-manual.
   * @param manualID ID of the target-manual
   * @returns file-status as FileStatus-Object
   */
   getPreliminaryNoteStatus(manualID: number): Observable<FileStatus> {
    return this.http.get<FileStatus>(this.apiURL + '/module-manuals/' + manualID + "/preliminary-note").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/preliminary-note"-api-endpoint per PUT.
   * Used to set / upload a preliminary-note for the module-manual with the given ID.
   * @param manualID ID of the target-manual
   * @param file the preliminary-note as .txt or .odt file
   * @returns
   */
  uploadPreliminaryNote(manualID: number, file: File): Observable<FileStatus> {
    const formData: FormData = new FormData();
    formData.append('preliminaryNoteFile', file, file.name);

    return this.http.post<FileStatus>(this.apiURL + "/module-manuals/" + manualID + "/preliminary-note", formData).pipe(retry(1), catchError(this.handleError));
  }


  // ########## Module-Manuals-API: Segments ##########

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/segments"-api-endpoint per GET.
   * Returns a list of all segments for a given module-manual.
   * @param manualID ID of the target-manual
   * @returns Segments as Assignment[]
   */
  getSegments(manualID: number): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(this.apiURL + '/module-manuals/' + manualID + "/segments").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/segments"-api-endpoint per PUT.
   * Updates the set segments for a given module-manual to the submitted list.
   * @param manualID ID of the target-manual
   * @param segments the new segment-list as Assignment[]
   * @returns Segments as Assignment[]
   */
  updateSegments(manualID: number, segments: Assignment[]): Observable<Assignment[]> {
    return this.http.put<Assignment[]>(this.apiURL + '/module-manuals/' + manualID + "/segments", JSON.stringify(segments), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/module-types"-api-endpoint per GET.
   * Returns a list of all module-types for a given module-manual.
   * @param manualID ID of the target-manual
   * @returns Module-types as Assignment[]
   */
  getModuleTypes(manualID: number): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(this.apiURL + '/module-manuals/' + manualID + "/module-types").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/module-types"-api-endpoint per PUT.
   * Updates the set module-types for a given module-manual to the submitted list.
   * @param manualID ID of the target-manual
   * @param moduleTypes the new module-type-list as Assignment[]
   * @returns Module-types as Assignment[]
   */
  updateModuleTypes(manualID: number, moduleTypes: Assignment[]): Observable<Assignment[]> {
    return this.http.put<Assignment[]>(this.apiURL + '/module-manuals/' + manualID + "/module-types", JSON.stringify(moduleTypes), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/requirements"-api-endpoint per GET.
   * Returns a list of all requirements for a given module-manual.
   * @param manualID ID of the target-manual
   * @returns Requirements as string[]
   */
  getRequirements(manualID: number): Observable<string[]> {
    return this.http.get<string[]>(this.apiURL + '/module-manuals/' + manualID + "/requirements").pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "/module-manuals/{{manual_id}}/requirements"-api-endpoint per PUT.
   * Updates the set requirements for a given module-manual to the submitted list.
   * @param manualID ID of the target-manual
   * @param requirements the new requirements-list as string[]
   * @returns Requirements as string[]
   */
  updateRequirements(manualID: number, requirements: string[]): Observable<string[]> {
    return this.http.put<string[]>(this.apiURL + '/module-manuals/' + manualID + "/requirements", JSON.stringify(requirements), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }


  // ########## SPO-API ##########

  /**
   * Method for requesting the "{{url}}/spos"-api-endpoint per GET
   * Returns a list of all known spos.
   * @returns list of Spo-objects
   */
  getSPOs(): Observable<Spo[]> {
    return this.http.get<Spo[]>(this.apiURL + '/spos').pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/spos"-api-endpoint per POST.
   * Adds a new Spo.
   * @param spo a Spo-object to create
   * @returns the created Spo-object
   */
  createSPO(spo: Spo): Observable<Spo>  {
    return this.http.post<Spo>(this.apiURL + '/spos', JSON.stringify(spo), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }


  // ########## College-Employee-API ##########

  /**
   * Method for requesting the "{{url}}/college-employees"-api-endpoint per GET
   * Returns a list of all known college-employees.
   * @returns list of CollegeEmployee-objects
   */
  getCollegeEmployees(): Observable<CollegeEmployee[]> {
    return this.http.get<CollegeEmployee[]>(this.apiURL + '/college-employees').pipe(retry(1), catchError(this.handleError));
  }

  /**
   * Method for requesting the "{{url}}/college-employees"-api-endpoint per POST.
   * Adds a new college-employee.
   * @param collegeEmployee a CollegeEmployee-object to create
   * @returns the created CollegeEmployee-object
   */
  createCollegeEmployee(collegeEmployee: CollegeEmployee): Observable<CollegeEmployee>  {
    return this.http.post<CollegeEmployee>(this.apiURL + '/college-employees', JSON.stringify(collegeEmployee), this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }


  // ########## Error-Handling ##########

  handleError(error: any) {
    let errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;

    window.alert(errorMessage);

    return throwError(() => {
      return errorMessage;
    });
  }
}
