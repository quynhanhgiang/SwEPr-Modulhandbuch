import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { Assignment } from '../shared/Assignments';
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

  // ### dialog-control ###
  display: boolean = false;                 // true: dialog is visible, false: dialog is invisible

  // ### form-groups ###
  moduleFormGroup: FormGroup;               //main-formgroup

  // ### asynchronous data ###
  newModule!:Module;                        //new generated Module
  profs!:CollegeEmployee[];                 //list of all created profs

  // ### form-controll ###
  selectedProfs!:[];
 
  // ### form-select ###
  // ### Multidimensional Arrays ###
  // admissionRequirements[0] = list of all admissionRequirements for the firstVariation
  // admissionRequirements[1] = list of all admissionRequirements for the secondVariation
  admissionRequirements:Assignment[][]=[];  //list of all moduleManuals for each Variation
  moduleTypes:Assignment[][]=[];            //list of all moduleManuals for each Variation
  segments:Assignment[][]=[];               //list of all moduleManuals for each Variation
  // ### Array ###
  moduleManuals!:ModuleManual[];            //list of all moduleManuals
  moduleOwners!:CollegeEmployee[];          //list of all ModuleOwners
  cycles!:String[];                         //list of all Cycles
  durations!:String[];                      //list of all duration
  languages!:String[];                      //list of all languages
  displayProfs:displayCollegeEmployee[]=[]; //list of all profs but with a converted name
  maternityProtections!:String[];           //list of all maternityProtections

  /**
   * 
   * @param fb formbuilder for moduleFormGroup
   * @param restAPI rest-api for submitting and receiving Data
   * @param router Router for redirecting to a diffrent url
   */
  constructor(private fb: FormBuilder, private restAPI: RestApiService, private router: Router) {
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

  /**
   * initalize all Data
   */
  ngOnInit(): void {
    this.selectedProfs=[];
    this.segments=[];
    this.moduleTypes=[];
    this.admissionRequirements=[];

    //get all profs and convert them do a displayCollegeEmployee object
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

  /**
   * create new Module form the given input 
   * @param event submitevent wich contains the pressed Button
   */
  onSubmit(event: {submitter:any }): void {
    this.newModule = this.moduleFormGroup.value;

    //convert displayCollegeEmployees back to collegeEmployees
    for(let i=0;i<this.newModule.profs.length;i++){
      for(let j=0;j<this.profs.length;j++){
        if(this.newModule.profs[i].id==this.profs[j].id){
          this.newModule.profs[i]=this.profs[j];
          break;
        }
      }
    }

    //create new module
    this.restAPI.createModule(this.newModule).subscribe(resp => {
      this.onSuccessfulSubmission.emit();
      console.log(resp);

      this.hideDialog();

      //if pressed button had the id'btn-submit-new' then 
      if(event.submitter.id=="btn-submit-new"){
        this.showDialog();
      }

      //if pressed button had the id'btn-submit-open' then redirect to url
      if(event.submitter.id=="btn-submit-open"){
        this.router.navigate(['/module-detail-view',resp.id]);
      }
    });
  }
  
  /**
   * will be triggert if a manual is beeing selected
   * recives moduleType, Requirements and Segemts wich belongs to the specific selected manual
   * @param i index of variation
   */
  updateModuleManual( i:number) {
    this.restAPI.getModuleTypes(this.moduleFormGroup.value.variations[i].manual.id).subscribe(resp => {
      this.moduleTypes[i]=(resp);
    });

    this.restAPI.getRequirements(this.moduleFormGroup.value.variations[i].manual.id).subscribe(resp => {
      this.admissionRequirements[i]= resp;
    });

    this.restAPI.getSegments(this.moduleFormGroup.value.variations[i].manual.id).subscribe(resp => {
      this.segments[i]=resp    
    });
  }

  /**
   * return all variation as an FormArray
   */
  get variations(){
    return (this.moduleFormGroup.get("variations") as FormArray);
  }

  /**
   * create a new variation as a formGroup
   */
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

  /**
   * delete variation a index i
   * @param index index of variation
   */
  deleteVariation(index:number){
    this.variations.removeAt(index);

    //removes list from Multidimensional Array and close the gap
    this.moduleTypes.splice(index,1)
    this.admissionRequirements.splice(index,1)
    this.segments.splice(index,1)
  }

  //shows pop-up-dialog
  showDialog() {
    this.display = true;
  }

  //hides pop-up-dialog and reset Component
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
