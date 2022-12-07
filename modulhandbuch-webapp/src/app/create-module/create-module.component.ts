import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { ModuleManual } from '../shared/module-manual';
import { DisplayModuleManual } from './display-module-manual';
import { displayCollegeEmployee } from './displayCollegeEmployee';

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
  displayProfs:displayCollegeEmployee[]=[];
  selectedProfs!:[];
  moduleManuals!:ModuleManual[];
  displayModuleManuals:DisplayModuleManual[]=[];
  moduleOwners!:CollegeEmployee[];
  displayModuleOwners:displayCollegeEmployee[]=[];
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
    this.loaded=0;
    //this.disabled[0]=true;
    this.selectedProfs=[];
    this.segments=[];
    this.moduleTypes=[];
    this.admissionRequirements=[];

    this.restAPI.getCollegeEmployees().subscribe(resp => {
      if(resp.length<1){
        window.alert("Es muss zuerst ein Mitarbeiter angelegt werden, bevor weitere Module angelegt werden können")
        return;
      }else{
        this.profs = resp;
      
        for (let i=0;i<resp.length;i++) {
          let displayProf:DisplayModuleManual={id:resp[i].id, name:""};
          displayProf.name=this.profs[i].title +" " + this.profs[i].firstName +" " +this.profs[i].lastName;
          this.displayProfs.push(displayProf);
        }
        this.loaded++;
      }
    });

    this.restAPI.getCollegeEmployees().subscribe(resp => {
      if(resp.length<1){
        window.alert("Es muss zuerst ein Mitarbeiter angelegt werden, bevor weitere Module angelegt werden können")
        return;
      }else{
        this.moduleOwners = resp;
      
        for (let i=0;i<resp.length;i++) {
          let displayModuleOwner:DisplayModuleManual={id:resp[i].id, name:""};
          displayModuleOwner.name = this.moduleOwners[i].title +" " + this.moduleOwners[i].firstName +" " +this.moduleOwners[i].lastName;
          this.displayModuleOwners.push(displayModuleOwner);
        }
        this.loaded++;
      }

    });

    this.restAPI.getModuleManuals().subscribe(resp => {
      if(resp.length<1){
        window.alert("Es muss zuerst ein Modulhandbuch angelegt werden, bevor weitere Module angelegt werden können")
        return;
      }else{
        this.moduleManuals = resp;
      
        for (let i=0;i<resp.length;i++) {
          let displayModuleManual:DisplayModuleManual={id:resp[i].id, name:""};
          if(resp[i].spo.endDate==null){
            displayModuleManual.name=resp[i].spo.degree +" "+resp[i].spo.course+"\n (ab: "+resp[i].spo.startDate+")";
          }else{
            displayModuleManual.name=resp[i].spo.degree +" "+resp[i].spo.course+"\n ("+resp[i].spo.startDate+"-"+resp[i].spo.endDate+")";
          }
          
          this.displayModuleManuals.push(displayModuleManual);
        }
        this.loaded++;
      }
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
    this.newModule = this.moduleFormGroup.value;

    if(this.newModule.profs.length<1){
      window.alert("Es muss mindestens ein Dozent zugewiesen werden");
      return;
    }
    
    for(let i=0;i<this.newModule.variations.length;i++){
      let index=this.newModule.variations[i].manual.id;
      for(let j=0;j < this.moduleManuals.length;j++){
        if(this.moduleManuals[j].id==index){
          this.newModule.variations[i].manual=this.moduleManuals[j];
          break;
        }
      }
    }

    for(let i=0;i<this.moduleOwners.length;i++){
      if(this.newModule.moduleOwner.id==this.moduleOwners[i].id){
        this.newModule.moduleOwner = this.moduleOwners[i];
        break;
      }
    }

    for(let i=0;i<this.newModule.profs.length;i++){
      for(let j=0;j<this.profs.length;i++){
        if(this.newModule.profs[i].id=this.profs[j].id){
          this.newModule.profs[i]=this.profs[j];
          break;
        }
      }
    }

    this.restAPI.createModule(this.newModule).subscribe(resp => {
      console.log(resp);
    });
    
    this.hideDialog();
    this.resetForm();
    this.ngOnInit();

    if(event.submitter.id=="bt-submit-new"){
      this.showDialog();
    }
  }
  
  updateModuleManual(id:number, i:number) {
    this.restAPI.getModuleTypes(id).subscribe(resp => {
        this.moduleTypes[i]=(resp);
    });

    this.restAPI.getRequirements(id).subscribe(resp => {
      this.admissionRequirements[i]= resp;
    });

    this.restAPI.getSegments(id).subscribe(resp => {
      for(let j=0;j<resp.length;i++){
        this.segments[i]=resp;
      }
    });

    //delete when in dev
    if(i==0){
      this.moduleTypes[i]=["Wahlfach", "Pflichtfach", "Praktikum"]
    }else{
      if(i==1){
        this.moduleTypes[i]=["Wahlfach", "Pflichtfach"]
      }else{
        this.moduleTypes[i]=["Wahlfach"]
      }
    }

    //delete when in dev
    if(i==0){
      this.segments[i]=["1. Abschnitt", "2. Abschnitt", "3. Abschnitt"]
    }else{
      if(i==1){
        this.segments[i]=["1. Abschnitt", "4. Abschnitt"]
      }else{
        this.segments[i]=["1. Abschnitt"]
      }
    }

    //delete when in dev
    if(i==0){
      this.admissionRequirements[i]=["1", "2", "3"]
    }else{
      if(i==1){
        this.admissionRequirements[i]=["1", "4"]
      }else{
        this.admissionRequirements[i]=["100"]
      }
    }

    this.disabled[i]=false;
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
    
    this.disabled.push(true);//Test
  }

  deleteVariation(index:number){
    this.variations.removeAt(index);
    this.disabled.splice(index, 1)//testing
    this.moduleTypes.splice(index,1)//testing
    this.admissionRequirements.splice(index,1)//testing
    this.segments.splice(index,1)//Testing
  }

  showDialog() {//make form visible
    if(this.loaded==7){
      this.display = true;
    }else{
      window.alert("Es liegt kein Modulhandbuch vor, welchem das Modul zugeordent werden kann")
    }
    
  }

  hideDialog() {//hide form
    this.display = false;
    this.moduleFormGroup.reset();
    this.ngOnInit();
  }

  resetForm(){
    this.moduleFormGroup.reset();
    this.ngOnInit();
  }
}