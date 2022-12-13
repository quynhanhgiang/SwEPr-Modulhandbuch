import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { displayCollegeEmployee } from '../create-module/displayCollegeEmployee';
import { RestApiService } from '../services/rest-api.service';
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

  display: boolean = false;//false == form hidden | true == form visible
  moduleName!:string;
  rendered:boolean = false;

  newModule!:Module;
  
  moduleFormGroup: FormGroup;

  profs!:CollegeEmployee[];
  displayProfs:displayCollegeEmployee[]=[];
  selectedProfs:displayCollegeEmployee[]=[];

  moduleManuals!:ModuleManual[];
  moduleOwners!:CollegeEmployee[];

  cycles!:String[];
  durations!:String[];
  languages!:String[];
  maternityProtections!:String[];

  admissionRequirements:String[][]=[];
  moduleTypes:String[][]=[];
  segments:String[][]=[];
  
  private routeSub!: Subscription;
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

  ngOnInit(): void {
    console.log("init start")
    this.segments=[];
    this.moduleTypes=[];
    this.admissionRequirements=[];
    this.profs = [];

    let id=0;
    this.routeSub = this.route.params.subscribe(params => {
      id =params['id'];
    });

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

    this.restAPI.getModule(id).subscribe(module => {
      this.moduleName = module.moduleName;

      for (let i=0;i<module.profs.length;i++) {
        let displayProf:displayCollegeEmployee={id:module.profs[i].id, name:""};
        displayProf.name=module.profs[i].title +" " + module.profs[i].firstName +" " +module.profs[i].lastName;
        this.selectedProfs.push(displayProf);
      }

      this.moduleFormGroup.patchValue({
        id: module.id,
        moduleName: module.moduleName,
        abbreviation: module.abbreviation,
        cycle: module.cycle,
        duration: module.duration,
        moduleOwner: module.moduleOwner,
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

      for(let i=0;i<module.variations.length;i++){
        this.variations.push(
          this.fb.group({
            manual:module.variations[i].manual,
            ects: module.variations[i].ects,
            sws: module.variations[i].sws,
            workLoad: module.variations[i].workLoad,
            semester: module.variations[i].semester,
            moduleType: module.variations[i].moduleType,
            admissionRequirement: module.variations[i].admissionRequirement,
            segment: module.variations[i].segment,
          })
        );
        let id =module.variations[i].manual.id
        if(id!=null){
          this.updateModuleManual(id,i);
        }
      }
      this.rendered = true;
    });
    console.log("init end")
  }

  onSubmit(): void {//create new Module with form data
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

    this.restAPI.updateModule(this.newModule).subscribe(resp => {
      console.log(resp);
      this.onSuccessfulSubmission.emit(); 
      window.location.reload();
    });
  }
  
  updateModuleManual(id:number, i:number) {
    this.restAPI.getModuleTypes(id).subscribe(resp => {
        this.moduleTypes[i]=(resp);
    });

    this.restAPI.getRequirements(id).subscribe(resp => {
      this.admissionRequirements[i]= resp;
    });

    this.restAPI.getSegments(id).subscribe(resp => {
      this.segments[i]=resp    
    });
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
    if(this.variations.length >1){
      this.variations.removeAt(index);
      this.moduleTypes.splice(index,1)//testing
      this.admissionRequirements.splice(index,1)//testing
      this.segments.splice(index,1)//Testing
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

}
