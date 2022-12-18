import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';
import { displayCollegeEmployee } from './displayCollegeEmployee';

@Component({
  selector: 'app-create-module',
  templateUrl: './create-module.component.html',
  styleUrls: ['./create-module.component.scss']
})
export class CreateModuleComponent implements OnInit {
  @Output() onSuccessfulSubmission = new EventEmitter();

  display: boolean = false;//false == form hidden | true == form visible

  newModule!:Module;

  moduleFormGroup: FormGroup;

  profs!:CollegeEmployee[];
  displayProfs:displayCollegeEmployee[]=[];
  selectedProfs!:[];
  moduleManuals!:ModuleManual[];
  moduleOwners!:CollegeEmployee[];
  cycles!:String[];
  durations!:String[];
  languages!:String[];
  maternityProtections!:String[];

  admissionRequirements:String[][]=[];
  moduleTypes:String[][]=[];
  segments:String[][]=[];

  constructor(private fb: FormBuilder, private restAPI: RestApiService) {
    this.moduleFormGroup = this.fb.group({
      id: null,
      moduleName: new FormControl(),
      abbreviation: new FormControl(),
      variations: this.fb.array([]),
      cycle: new FormControl(),
      duration: new FormControl(),
      moduleOwner: new FormControl(),
      profs: new FormControl(),
      language: new FormControl(),
      usage: new FormControl(),
      knowledgeRequirements: new FormControl(""),
      skills: new FormControl(),
      content: new FormControl(),
      examType: new FormControl(),
      certificates: new FormControl(),
      mediaType: new FormControl(),
      literature: new FormControl(),
      maternityProtection: new FormControl(),
    });
  }

  ngOnInit(): void {
    this.selectedProfs=[];
    this.segments=[];
    this.moduleTypes=[];
    this.admissionRequirements=[];

    this.restAPI.getCollegeEmployees().subscribe(resp => {
        this.profs = resp;

        for (let i=0;i<resp.length;i++) {
          let displayProf:displayCollegeEmployee={id:resp[i].id, name:""};
          displayProf.name=this.profs[i].title +" " + this.profs[i].firstName +" " +this.profs[i].lastName;
          this.displayProfs.push(displayProf);
        }
    });

    this.restAPI.getCollegeEmployees().subscribe(resp => {
        this.moduleOwners = resp;
    });

    this.restAPI.getModuleManuals().subscribe(resp => {
        this.moduleManuals = resp;
    });

    this.restAPI.getCycles().subscribe(resp => {
      this.cycles = resp;
    });

    this.restAPI.getDurations().subscribe(resp => {
      this.durations = resp;
    });

    this.restAPI.getMaternityProtections().subscribe(resp => {
      this.maternityProtections = resp;

    });

    this.restAPI.getLanguages().subscribe(resp => {
      this.languages = resp;
    });

    this.addVariation();
  }

  onSubmit(event: {submitter:any }): void {//create new Module with form data
    this.newModule = this.moduleFormGroup.value;

    if(this.newModule.profs.length<1){
      window.alert("Es muss mindestens ein Dozent zugewiesen werden");
      return;
    }

    for(let i=0;i<this.newModule.profs.length;i++){
      for(let j=0;j<this.profs.length;j++){
        if(this.newModule.profs[i].id==this.profs[j].id){
          this.newModule.profs[i]=this.profs[j];
          break;
        }
      }
    }

    this.restAPI.createModule(this.newModule).subscribe(resp => {
      this.onSuccessfulSubmission.emit();
      console.log(resp);
    });

    this.hideDialog();

    if(event.submitter.id=="btn-submit-new"){
      this.showDialog();
    }
  }

  updateModuleManual(id:number, i:number) {
    /*this.restAPI.getModuleTypes(id).subscribe(resp => {
        this.moduleTypes[i]=(resp);
    });

    this.restAPI.getRequirements(id).subscribe(resp => {
      this.admissionRequirements[i]= resp;
    });

    this.restAPI.getSegments(id).subscribe(resp => {
      this.segments[i]=resp
    });*/
  }

  get variations(){
    return (this.moduleFormGroup.get("variations") as FormArray);
  }

  addVariation() {
    this.variations.push(
      this.fb.group({
        manual: new FormControl(),
        ects: new FormControl(),
        sws: new FormControl(),
        workLoad: new FormControl(),
        semester: new FormControl(),
        moduleType: new FormControl(),
        admissionRequirement: new FormControl(),
        segment:new FormControl(),
      })
    );
  }

  deleteVariation(index:number){
    this.variations.removeAt(index);
    this.moduleTypes.splice(index,1)
    this.admissionRequirements.splice(index,1)
    this.segments.splice(index,1)
  }

  showDialog() {//make form visible
    this.display = true;
  }

  hideDialog() {//hide form
    this.display = false;
    this.moduleFormGroup.reset();
    this.selectedProfs=[];

    while(this.variations.length>0){
      this.deleteVariation(this.variations.length-1);
    }
    this.addVariation();
  }
}
