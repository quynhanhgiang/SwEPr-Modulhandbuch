import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-create-module',
  templateUrl: './create-module.component.html',
  styleUrls: ['./create-module.component.scss']
})
export class CreateModuleComponent implements OnInit {
  
  display: boolean = false;//false == form hidden | true == form visible
  loaded:number=0;
  disabled:boolean[]=[];

  newModule!:Module;
  
  moduleFormGroup: FormGroup;

  profs!:CollegeEmployee[];
  selectedProfs!:[];
  moduleManuals!:ModuleManual[];
  moduleOwners!:CollegeEmployee[];
  cycles!:String[];
  durations!:String[];
  languages!:String[];
  maternityProtections!:String[];

  admissionRequirements:String[][]=[];
  moduleCategorys:String[][]=[];
  segments:String[][]=[];

  constructor(private fb: FormBuilder, private restAPI: RestApiService) {
    this.moduleFormGroup = this.fb.group({
      id: null,
      moduleName: new FormControl('', [Validators.required]),
      abbreviation: new FormControl('', [Validators.required]),
      variations: this.fb.array([]),
      cycle: new FormControl('', [Validators.required]),
      duration: new FormControl('', [Validators.required]),
      moduleOwner: new FormControl('', [Validators.required]),
      profs: new FormControl('', [Validators.required]),
      language: new FormControl('', [Validators.required]),
      usage: new FormControl(),
      knowledgeRequirements: new FormControl(),
      skills: new FormControl(),
      content: new FormControl(),
      examType: new FormControl('', [Validators.required]),
      certificates: new FormControl(),
      mediaType: new FormControl(),
      literature: new FormControl(),
      maternityProtection: new FormControl('', [Validators.required]),
    });
  }
  
  ngOnInit(): void {
    this.restAPI.getCollegeEmployees().subscribe(resp => {
      this.profs = resp;
      this.loaded++;
    });

    this.restAPI.getCollegeEmployees().subscribe(resp => {
      this.moduleOwners = resp;
      this.loaded++;
    });

    this.restAPI.getModuleManuals().subscribe(resp => {
      this.moduleManuals = resp;
      this.loaded++;
    });

    this.restAPI.getCycles().subscribe(resp => {
      this.cycles = resp;
      this.loaded++;
    });

    this.restAPI.getDurations().subscribe(resp => {
      this.durations = resp;
      this.loaded++;
    });

    this.restAPI.getMaternityProtections().subscribe(resp => {
      this.maternityProtections = resp;
      this.loaded++;
    });

    this.restAPI.getLanguages().subscribe(resp => {
      this.languages = resp;
      this.loaded++;
    });

    this.addVariation();
  }

  onSubmit(event: {submitter:any }): void {//create new Module with form data
    console.log("submit");

    this.newModule = this.moduleFormGroup.value;
    
    this.restAPI.createModule(this.newModule).subscribe(resp => {
      console.log(resp);
    });
    
    this.hideDialog();
    this.resetForm();

    if(event.submitter.id=="bt-submit-new"){
      this.showDialog();
    }
  }

  updateModuleManual(id: string, i:number) {
    console.log("add MOduleManual with id: "+id+" i: "+i)
    this.restAPI.getModuleTypes(parseInt(id)).subscribe(resp => {
        this.moduleCategorys[i]=(resp);
    });

    this.restAPI.getRequirements(parseInt(id)).subscribe(resp => {
      this.admissionRequirements[i]= resp;
    });

    this.restAPI.getSegments(parseInt(id)).subscribe(resp => {
      for(let j=0;j<resp.length;i++){
        this.segments[i]=resp;
      }
    });

    this.disabled[i]=false;
  }

  get variations(){
    return (this.moduleFormGroup.get("variations") as FormArray);
  }

  addVariation() {
    this.variations.push(
      this.fb.group({
        moduleManual: new FormControl('', [Validators.required]),
        ects: new FormControl('', [Validators.required]),
        sws: new FormControl('', [Validators.required]),
        workLoad: new FormControl('', [Validators.required]),
        semester: new FormControl('', [Validators.required]),
        moduleCategory: new FormControl('', [Validators.required]),
        admissionRequirement: new FormControl('', [Validators.required]),
        segment:new FormControl('', [Validators.required]),
      })
    );
    this.disabled.push(true);//Test
  }

  deleteVariation(index:number){
    if(this.variations.length >1){
      this.variations.removeAt(index);
      delete this.disabled[index];//testing
      delete this.moduleCategorys[index]//testing
      delete this.admissionRequirements[index]//testing
      delete this.segments[index]//Testing
    }else{
      window.alert("Es muss mindestens eine Variation vorhanden sein")
    }
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
}