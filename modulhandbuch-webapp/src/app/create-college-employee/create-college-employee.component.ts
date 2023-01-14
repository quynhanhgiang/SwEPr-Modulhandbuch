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

  // ### dialog-control ###
  display: boolean = false;             // true: dialog is visible, false: dialog is invisible

  // ### form-groups ###
  employeeFormGroup: FormGroup;         //main-formgroup

  // ### form-controll ###
  doubleName:Boolean=false;

  // ### asynchronous data ###
  newCollegeEmployee!:CollegeEmployee;  //new generated employee
  employees!:CollegeEmployee[];         //list of all created employees
  title:string=""                       //string to transform title to correct format

  // ### form-select ###
  titles:string[]=[];                   //list of all created titles
  genders:string[]=[];                  //list of all created genders

  /**
   * 
   * @param fb formbuilder for employeeFormGroup
   * @param restAPI rest-api for submitting and receiving Data
   * @param confirmationService Confirmation service for the confirm pop-up-dialog
   */
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

  /**
   * create List of all availible genders and titles
   * get all created employees
   */
  ngOnInit(): void {
    this.titles=["Prof.","Dr.", "Dipl."]
    this.genders=["Herr","Frau", "Divers"]

    this.restAPI.getCollegeEmployees().subscribe(employees => {
      this.employees = employees;
    });
  }

  /**
   * 
   * @returns nothing
   */
  onSubmit(): void {

    //reset title
    this.newCollegeEmployee = this.employeeFormGroup.value;
    if(this.newCollegeEmployee.title==null){
      this.newCollegeEmployee.title="";
    }

    //check if a employee with the same email is already created
    for(let employee of this.employees){
      if(employee.email==this.newCollegeEmployee.email){
        window.alert("Ein Nutzer mit dieser Email-Adresse ist bereits angelegt")
        return;
      }
    }

    //check if a employee with the same name is already created and duplicate name is not checked
    for(let employee of this.employees){
      if(employee.firstName+employee.lastName==this.newCollegeEmployee.firstName+this.newCollegeEmployee.lastName&&this.doubleName==false){
        this.confirm();
        return;
      }
    }

    //reset duplicate Name check
    this.doubleName=false;

    //put title into the corretc format
    if(this.newCollegeEmployee.title.length>0){
      for (let i=0;i<this.newCollegeEmployee.title.length;i++) {
        this.title +=this.newCollegeEmployee.title[i]+" ";
      }
      this.title=this.title.slice(0,-1);
      this.newCollegeEmployee.title=this.title;
    }

    //check if title is "Divers"
    if(this.newCollegeEmployee.gender=="Divers"){
      this.newCollegeEmployee.gender="";
    }

    //create new employee
    this.restAPI.createCollegeEmployee(this.newCollegeEmployee).subscribe(resp => {
      console.log(resp);
      this.onSuccessfulSubmission.emit();
    });

    //reste Component
    this.title="";
    this.hideDialog();
    this.resetForm();
    this.ngOnInit();
  }

  //opens confirmation-dialog 
  confirm(): void{
    this.confirmationService.confirm({
        message: 'Ein Nutzer mit diesem Namen wurde schon angelegt. Wollen sie trozdem fortfahren?',
    });
  }

  //is beeing called when duplicate name is accepted
  accept(){
    //set duplicate Name check to true
    this.doubleName = true;          
    this.confirmationService.close();
    this.onSubmit();
  }

  //is beeing called when duplicate name is rejected. resets component
  reject(){
    this.title="";
    this.employeeFormGroup.reset();
    this.doubleName = false;
    this.confirmationService.close();
  }

  //reset main Form group
  resetForm(){
    this.employeeFormGroup.reset();
  }

  //shows pop-up-dialog
  showDialog() {
    this.display = true;
  }

  //hides pop-up-dialog
  hideDialog() {
    this.display = false;
  }
}