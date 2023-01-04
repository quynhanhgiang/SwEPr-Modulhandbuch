import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';

@Component({
  selector: 'app-create-college-employee',
  templateUrl: './create-college-employee.component.html',
  styleUrls: ['./create-college-employee.component.scss']
})
export class CreateCollegeEmployeeComponent implements OnInit {
  @Output() onSuccessfulSubmission = new EventEmitter();

  display: boolean = false;//false == form hidden | true == form visible

  newCollegeEmployee!:CollegeEmployee;
  employees!:CollegeEmployee[];

  employeeFormGroup: FormGroup;
  titles:string[]=[];
  genders:string[]=[];
  title:string=""
  doubleName:Boolean=false;

  constructor(private fb: FormBuilder, private restAPI: RestApiService,private confirmationService: ConfirmationService) {
    this.employeeFormGroup = this.fb.group({
      id: null,
      title:new FormControl(""),
      firstName:new FormControl(),
      lastName:new FormControl(),
      email:new FormControl(),
      gender:new FormControl()
    });
  }

  ngOnInit(): void {
    this.titles=["Prof.","Dr.", "Dipl."]
    this.genders=["Herr","Frau", "Divers"]

    this.restAPI.getCollegeEmployees().subscribe(employees => {
      this.employees = employees;
    });
  }

  onSubmit(): void {//create new Module with form data


    this.newCollegeEmployee = this.employeeFormGroup.value;
    if(this.newCollegeEmployee.title==null){
      this.newCollegeEmployee.title="";
    }

    for(let employee of this.employees){
      if(employee.email==this.newCollegeEmployee.email){
        window.alert("Ein Nutzer mit dieser Email-Adresse ist bereits angelegt")
        return;
      }
    }

    for(let employee of this.employees){
      if(employee.firstName+employee.lastName==this.newCollegeEmployee.firstName+this.newCollegeEmployee.lastName&&this.doubleName==false){
        this.confirm();
        return;
      }
    }

    this.doubleName=false;

    if(this.newCollegeEmployee.title.length>0){
      for (let i=0;i<this.newCollegeEmployee.title.length;i++) {
        this.title +=this.newCollegeEmployee.title[i]+" ";
      }
      this.title=this.title.slice(0,-1);
      this.newCollegeEmployee.title=this.title;
    }

    if(this.newCollegeEmployee.gender=="Divers"){
      this.newCollegeEmployee.gender="";
    }

    this.restAPI.createCollegeEmployee(this.newCollegeEmployee).subscribe(resp => {
      console.log(resp);
      this.onSuccessfulSubmission.emit();
    });

    this.title="";
    this.hideDialog();
    this.resetForm();
    this.ngOnInit();
  }

  confirm(): void{
    this.confirmationService.confirm({
        message: 'Ein Nutzer mit diesem Namen wurde schon angelegt. Wollen sie trozdem fortfahren?',
    });
  }

  accept(){
    this.doubleName = true;
    this.confirmationService.close();
    this.onSubmit();
  }

  reject(){
    this.title="";
    this.employeeFormGroup.reset();
    this.doubleName = false;
    this.confirmationService.close();
  }

  resetForm(){
    this.employeeFormGroup.reset();
  }

  showDialog() {//make form visible
    this.display = true;
  }

  hideDialog() {//hide form
    this.display = false;
  }

}