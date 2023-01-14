import { Component, OnInit } from '@angular/core';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { displayCollegeEmployee } from './display-college-employee';

@Component({
  selector: 'app-get-college-employees',
  templateUrl: './get-college-employees.component.html',
  styleUrls: ['./get-college-employees.component.scss']
})
export class GetCollegeEmployeesComponent implements OnInit {
  
  // ###  dataview-control ###
  loaded:boolean=false;                             //all data is loaded, true: dataview is visible, false: dataview is invisible
  message:string ="Mitarbeiter werden geladen...";  //message wich will be shown then the dataview shows no entrys

  // ### asynchronous data ###
  employees:CollegeEmployee[]=[];                   //list of all created employees
  displayEmployees:displayCollegeEmployee[]=[];     //list of all created employees with a display Object
  
  /**
   * 
   * @param restAPI rest-api for submitting and receiving Data
   */
  constructor(private restAPI: RestApiService) { }

  /**
   * initalize all Data
   */
  ngOnInit(): void {
    this.displayEmployees=[];
    this.loaded=false;

    //recive all created employees
    this.restAPI.getCollegeEmployees().subscribe(employees => {
      this.employees = employees;

      //if employees have been created then
      if(employees.length>0){
        
        //convert collegeEmployee object to displayCollegeEmployee Object
        for (let i=0;i<employees.length;i++) {
          let displayEmployee:displayCollegeEmployee={id:0,name:"", email:"", initials:""};
          displayEmployee.id=employees[i].id;
          displayEmployee.name= employees[i].title+" "+employees[i].firstName+" "+employees[i].lastName;
          displayEmployee.email=employees[i].email;
          displayEmployee.initials=employees[i].firstName[0]+employees[i].lastName[0];
  
          this.displayEmployees.push(displayEmployee);
        }

        //update massage
        this.message = "Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe."
      }else{

        //no employees have been created so update message
        this.message = "Es wurden noch keine Mitarbeiter angelegt. Zum Anlegen bitte auf 'Neuen Mitarbeiter anlegen' klicken."
      }

      //all data has been loaded so set loaded to true
      this.loaded=true;
    });

  }
}
