import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { displayCollegeEmployee } from '../create-module/displayCollegeEmployee';
import { RestApiService } from '../services/rest-api.service';
import { Assignment } from '../shared/Assignments';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';

@Component({
  selector: 'app-edit-module',
  templateUrl: './edit-module.component.html',
  styleUrls: ['./edit-module.component.scss']
})
export class EditModuleComponent implements OnInit {
  @Output() onSuccessfulSubmission = new EventEmitter();

  // ### dialog-control ###
  display: boolean = false;                 //true: dialog is visible, false: dialog is invisible

  // ### container-control ###
  rendered:boolean = false;                 //true: container is visible, false: container is invisible

  // ### asynchronous data ###
  editModule!:Module;                       //module to be edited
  profs!:CollegeEmployee[];                 //list of all created profs
  moduleName!:string;                       //name of the module to be edited
  private routeSub!: Subscription;          //Subscription to acces url parameters

  // ### form-groups ###
  moduleFormGroup: FormGroup;               //main-formgroup

  // ### form-controll ###
  selectedProfs:displayCollegeEmployee[]=[];// selected Profs of then loaded Module

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
   * @param router Router for reciving the module id form the url
  */
  constructor(private fb: FormBuilder, private restAPI: RestApiService, private route: ActivatedRoute) {
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
    //reset compontent
    this.segments=[];
    this.moduleTypes=[];
    this.admissionRequirements=[];
    this.profs = [];
    let id=0;

    //get id of module form url
    this.routeSub = this.route.params.subscribe(params => {
      id =params['id'];
    });

    //get a list of all employees
    this.restAPI.getCollegeEmployees().subscribe(resp => {
        this.profs = resp;

        //convert collegeEmployees to displayCollegeEmployees
        for (let i=0;i<resp.length;i++) {
          let displayProf:displayCollegeEmployee={id:resp[i].id, name:""};
          displayProf.name=this.profs[i].title +" " + this.profs[i].firstName +" " +this.profs[i].lastName;
          this.displayProfs.push(displayProf);
        }
    });

    //nested Rest-Api-Calls so it is garanteed that all needed data has been loaded
    this.restAPI.getModuleManuals().subscribe(resp => {
        this.moduleManuals = resp;

        this.restAPI.getCollegeEmployees().subscribe(resp => {
          this.moduleOwners = resp;
  
          //get Module by id
          this.restAPI.getModule(id).subscribe(module => {
            this.moduleName = module.moduleName;
      
            //convert all selected profs of type collegeEmployee to type displayCollegeEmployee
            for (let i=0;i<module.profs.length;i++) {
              let displayProf:displayCollegeEmployee={id:module.profs[i].id, name:""};
              displayProf.name=module.profs[i].title +" " + module.profs[i].firstName +" " +module.profs[i].lastName;
              this.selectedProfs.push(displayProf);
            }
      
            //update moduleFormGroup values with the values of the recived module
            this.moduleFormGroup.patchValue({
              id: module.id,
              moduleName: module.moduleName,
              abbreviation: module.abbreviation,
              cycle: module.cycle,
              duration: module.duration,
              moduleOwner: this.moduleOwners.find(item => item.id == module.moduleOwner?.id) ?? null,
              profs: module.profs,
              language: module.language,
              usage: module.usage,
              knowledgeRequirements: module.knowledgeRequirements,
              skills: module.skills,
              content: module.content,
              examType: module.examType,
              certificates: module.certificates,
              mediaType: module.mediaType,
              literature: module.literature,
              maternityProtection: module.maternityProtection,
            });
      
            //for every variation in module do
            for(let i=0;i<module.variations.length;i++){

              //get id form moduleManual
              let id =module.variations[i].manual.id
      
              if(id!=null){

                //nested Rest-Api-Calls so it is garanteed that all needed data has been loaded
                this.restAPI.getModuleTypes(id).subscribe(resp => {
                  this.moduleTypes[i]=(resp);
                  this.restAPI.getRequirements(id!).subscribe(resp => {
                    this.admissionRequirements[i]= resp;
                    this.restAPI.getSegments(id!).subscribe(resp => {
                      this.segments[i]=resp
                      
                      //create variation and add it to the moduleform group
                      this.variations.push(
                        this.fb.group({
                          manual:this.moduleManuals.find(item => item.id == module.variations[i].manual?.id) ?? null,
                          ects: module.variations[i].ects,
                          sws: module.variations[i].sws,
                          workLoad: module.variations[i].workLoad,
                          semester: module.variations[i].semester,
                          moduleType: this.moduleTypes[i].find(item => item.id == module.variations[i].moduleType?.id) ?? null,
                          admissionRequirement: this.admissionRequirements[i].find(item => item.id == module.variations[i].admissionRequirement?.id) ?? null,
                          segment: this.segments[i].find(item => item.id == module.variations[i].segment?.id) ?? null,
                        })
                     );
                    });
                  });
                });
              }
            }
            //all neded data has been loaded container can be visible
            this.rendered = true;
          });
      });
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
  }

  /**
   * opdate new Module form the given input 
   * @param event submitevent wich contains the pressed Button
  */
  onSubmit(): void {//create new Module with form data
    this.editModule = this.moduleFormGroup.value;

    //convert displayCollegeEmployees back to collegeEmployees
    for(let i=0;i<this.editModule.profs.length;i++){
      for(let j=0;j<this.profs.length;j++){
        if(this.editModule.profs[i].id==this.profs[j].id){
          this.editModule.profs[i]=this.profs[j];
          break;
        }
      }
    }

    //update module
    this.restAPI.updateModule(this.editModule).subscribe(resp => {
      console.log(resp);
      this.hideDialog();
      this.onSuccessfulSubmission.emit();
    });
  }
  
  /**
   * will be triggert if a manual is beeing selected
   * recives moduleType, Requirements and Segemts wich belongs to the specific selected manual
   * @param i index of variation
  */
  updateModuleManual(i:number) {
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
      this.moduleTypes.splice(index,1);
      this.admissionRequirements.splice(index,1);
      this.segments.splice(index,1);
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
