import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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

  employeeFormGroup: FormGroup;
  titles:string[]=[];
  genders:string[]=[];
  title:string=""

  constructor(private fb: FormBuilder, private restAPI: RestApiService) {
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
    this.genders=["Herr","Frau", "Diverse"]
  }

  onSubmit(): void {//create new Module with form data
    console.log("submit");

    this.newCollegeEmployee = this.employeeFormGroup.value;

    if(this.newCollegeEmployee.title.length>0){
      for (let i=0;i<this.newCollegeEmployee.title.length;i++) {
        this.title +=this.newCollegeEmployee.title[i]+" ";
      }
      this.title=this.title.slice(0,-1);
      this.newCollegeEmployee.title=this.title;
    }

    if(this.newCollegeEmployee.gender=="Diverse"){
      this.newCollegeEmployee.gender="";
    }

    this.restAPI.createCollegeEmployee(this.newCollegeEmployee).subscribe(resp => {
      console.log(resp);
      this.onSuccessfulSubmission.emit();
    });

    this.title="";
    this.hideDialog();
    this.resetForm();
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
