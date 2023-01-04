import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api/selectitem';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { displayCollegeEmployee } from './display-college-employee';

@Component({
  selector: 'app-get-college-employees',
  templateUrl: './get-college-employees.component.html',
  styleUrls: ['./get-college-employees.component.scss']
})
export class GetCollegeEmployeesComponent implements OnInit {
  
  loaded:boolean=false;
  employees:CollegeEmployee[]=[];
  displayEmployees:displayCollegeEmployee[]=[];
  message:string ="Mitarbeiter werden geladen...";

  constructor(private restAPI: RestApiService) { }

  ngOnInit(): void {
    this.displayEmployees=[];
    this.loaded=false;
    this.restAPI.getCollegeEmployees().subscribe(employees => {
      this.employees = employees;
      if(employees.length>0){
        for (let i=0;i<employees.length;i++) {
          let displayEmployee:displayCollegeEmployee={id:0,name:"", email:"", initials:""};
          displayEmployee.id=employees[i].id;
          displayEmployee.name= employees[i].title+" "+employees[i].firstName+" "+employees[i].lastName;
          displayEmployee.email=employees[i].email;
          displayEmployee.initials=employees[i].firstName[0]+employees[i].lastName[0];
  
          this.displayEmployees.push(displayEmployee);
        }
        this.message = "Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe."
      }else{
        this.message = "Es wurden noch keine Mitarbeiter angelegt. Zum Anlegen bitte auf 'Neuen Mitarbeiter anlegen' klicken."
      }
      this.loaded=true;
    });

  }
}
