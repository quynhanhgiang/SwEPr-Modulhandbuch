import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { CollegeEmployee } from '../shared/CollegeEmployee';
import { Module } from '../shared/module';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-edit-module',
  templateUrl: './edit-module.component.html',
  styleUrls: ['./edit-module.component.scss']
})
export class EditModuleComponent implements OnInit {

  moduleName!:string;

  display: boolean = false;//false == form hidden | true == form visible
  rendered:boolean=false;

  moduleFormGroup: FormGroup;

  profs!:CollegeEmployee[];
  selectedProfs!:CollegeEmployee[];
  spos!:Spo[];
  moduleOwners!:CollegeEmployee[];

  private routeSub!: Subscription;
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private restAPI: RestApiService) {
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
      admissionRequirements: new FormControl(),
      knowledgeRequirements: new FormControl(),
      skills: new FormControl(),
      content: new FormControl(),
      examType: new FormControl(),
      certificates: new FormControl(),
      mediaType: new FormControl(),
      literature: new FormControl(),
      maternityProtection: new FormControl(),
    });
  }

  ngOnInit():void {
    this.profs=[
      {id:0, firstName:"Volkhard", lastName:"Pfeiffer", title:"Prof.", gender:"Herr", email:"volkhard.pfeiffer@hs-coburg.de"},
      {id:1, firstName:"Dieter", lastName:"Landes", title:"Prof. Dr.", gender:"Herr", email:"dieter.landes@hs-coburg.de"},
      {id:2, firstName:"Dieter", lastName:"Wießmann", title:"Prof. Dr.", gender:"Herr", email:"dieter.wissmann@hs-coburg.de"},
      {id:3, firstName:"Thomas", lastName:"Wieland", title:"Prof. Dr.", gender:"Herr", email:"thomas.wieland@hs-coburg.de"},
      {id:4, firstName:"Jens", lastName:"Grubert", title:"Prof. Dr.", gender:"Herr", email:"Jens.Grubert@hs-coburg.de"},
      {id:5, firstName:"Quirin", lastName:"Meyer", title:"Prof. Dr.", gender:"Herr", email:"Quirin.Meyer@hs-coburg.de"},
      {id:6, firstName:"Florian", lastName:"Mittag", title:"Prof. Dr.", gender:"Herr", email:"Florian.Mittag@hs-coburg.de"},
      {id:7, firstName:"Jürgen", lastName:"Terpin", title:"Prof. Dr.", gender:"Herr", email:"juergen.terpin@hs-coburg.de"},
      {id:8, firstName:"Matthias", lastName:"Mörz", title:"Prof. Dr.", gender:"Herr", email:"matthias.moerz@hs-coburg.de"},
    ]

    this.moduleOwners=[
      {id:0, firstName:"Volkhard", lastName:"Pfeiffer", title:"Prof.", gender:"Herr", email:"volkhard.pfeiffer@hs-coburg.de"},
      {id:1, firstName:"Dieter", lastName:"Landes", title:"Prof. Dr.", gender:"Herr", email:"dieter.landes@hs-coburg.de"},
      {id:2, firstName:"Dieter", lastName:"Wießmann", title:"Prof. Dr.", gender:"Herr", email:"dieter.wissmann@hs-coburg.de"},
      {id:3, firstName:"Thomas", lastName:"Wieland", title:"Prof. Dr.", gender:"Herr", email:"thomas.wieland@hs-coburg.de"},
      {id:4, firstName:"Jens", lastName:"Grubert", title:"Prof. Dr.", gender:"Herr", email:"Jens.Grubert@hs-coburg.de"},
      {id:5, firstName:"Quirin", lastName:"Meyer", title:"Prof. Dr.", gender:"Herr", email:"Quirin.Meyer@hs-coburg.de"},
      {id:6, firstName:"Florian", lastName:"Mittag", title:"Prof. Dr.", gender:"Herr", email:"Florian.Mittag@hs-coburg.de"},
      {id:7, firstName:"Jürgen", lastName:"Terpin", title:"Prof. Dr.", gender:"Herr", email:"juergen.terpin@hs-coburg.de"},
      {id:8, firstName:"Matthias", lastName:"Mörz", title:"Prof. Dr.", gender:"Herr", email:"matthias.moerz@hs-coburg.de"},
    ]

    this.spos=[
      {id:0, link:"https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2014/SPO_B_BW_6.pdf",startDate:"2014-08-01", endDate:"2020-08-01", course:"BW", degree:"Bachelor" },
      {id:1, link:"https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2021/SPO_B_IW_3.pdf",startDate:"2021-11-25", endDate:"2022-12-31", course:"IW",degree:"Bachelor" },
      {id:2, link:"https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2021/SPO_B_SA_9.pdf",startDate:"2014-12-23", endDate:"2022-12-31", course:"SA",degree:"Bachelor" },
      {id:3, link:"https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2022/SPO__B__ADT_2022.pdf",startDate:"2022-05-24", endDate:"2022-08-01", course:"ADT",degree:"Bachelor" },
      {id:4, link:"https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2021/SPO_B_ZT.pdf",startDate:"2021-05-06", endDate:"2022-01-01", course:"ZT",degree:"Bachelor" },
      {id:5,link: "https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2009/10_SPO_B_IF_2.pdf",startDate: "2009-07-22",endDate:null ,course: "IF",degree:"Bachelor"},
      {id:6,link: "https://www.hs-coburg.de/fileadmin/hscoburg/Amtsblatt/2009/10_SPO_B_IF_2.pdf",startDate: "2009-07-22",endDate:null ,course: "VC",degree:"Bachelor"},
    ]

    let id=0;
    this.routeSub = this.route.params.subscribe(params => {
      id =params['id'];
    });

    this.restAPI.getModule(id).subscribe(module => {
      this.moduleName = module.moduleName;
      this.selectedProfs = module.profs;

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
        admissionRequirements: module.admissionRequirements,
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
            spo:module.variations[i].manual.spo,
            ects: module.variations[i].ects,
            sws: module.variations[i].sws,
            workLoad: module.variations[i].workLoad,
            semester: module.variations[i].semester,
            category: module.variations[i].category,
          })
        );

      }
      this.rendered = true;
    });

  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  onSubmit(event: {submitter:any }): void {//create new Module with form data
    console.log("submit");

    this.restAPI.updateModule(this.moduleFormGroup.value).subscribe(resp => {
      console.log(resp);
    });

    this.hideDialog();

    if(event.submitter.id=="bt-submit-new"){
      this.showDialog();
    }
  }

  showDialog() {//make form visible
    this.display = true;
  }

  hideDialog() {//hide form
    this.display = false;
  }

  get variations(){
    return (this.moduleFormGroup.get("variations") as FormArray);
  }

  addVariation() {
    this.variations.push(
      this.fb.group({
        spo: new FormControl('', [Validators.required]),
        ects: new FormControl('', [Validators.required]),
        sws: new FormControl('', [Validators.required]),
        workLoad: new FormControl('', [Validators.required]),
        semester: new FormControl('', [Validators.required]),
        category: new FormControl('', [Validators.required]),
      })
    );
  }

  deleteVariation(index:number){
    if(this.variations.length >1){
      this.variations.removeAt(index);
    }else{
      alert("Es muss mindestens eine Variation geben")
    }
  }

}
