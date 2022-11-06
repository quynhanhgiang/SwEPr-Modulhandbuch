import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { RestApiService } from '../services/rest-api.service';
import { Module } from '../shared/module';

@Component({
  selector: 'app-create-module',
  templateUrl: './create-module.component.html',
  styleUrls: ['./create-module.component.scss']
})
export class CreateModuleComponent implements OnInit {
  
  display: boolean = false;//false == form hidden | true == form visible
  newModule!:Module;
  moduleFormGroup: FormGroup;

  constructor(private fb: FormBuilder, private restAPI: RestApiService) {
    this.moduleFormGroup = this.fb.group({
      id:null,
      moduleName:new FormControl(''),
      abbreviation:new FormControl(''),
      sws:new FormControl(''),
      ects:new FormControl(''),
      workLoad:new FormControl(''),
      semester:new FormControl(''),
      cycle:new FormControl(''),
      duration:new FormControl(''),
      moduleOwner:new FormControl(''),
      prof:new FormControl(''),
      language: new FormControl(''),
      usage:new FormControl(''),
      admissionRequirements:new FormControl(''),
      knowledgeRequirements:new FormControl(''),
      skills:new FormControl(''),
      content:new FormControl(''),
      examType:new FormControl(''),
      certificates:new FormControl(''),
      mediaType:new FormControl(''),
      literature:new FormControl(''),
      maternityProtection:new FormControl(''),
    });
  } 
  
  ngOnInit(): void {

  }

  showDialog() {//make form visible
    this.display = true;
  }

  hideDialog() {//hide form
    this.display = false;
  }

  resetForm(){
    this.moduleFormGroup.reset();
  }

  onSubmit(event: {submitter:any }): void {//create new Module with form data
    console.log("submit");
    this.newModule = new Module(this.moduleFormGroup.value);
    
    this.restAPI.createModule(this.newModule).subscribe(resp => {
      console.log(resp);
    });
    
    this.hideDialog();
    this.resetForm();

    if(event.submitter.id=="bt-submit-new"){
      this.showDialog();
    }
  }

 

}