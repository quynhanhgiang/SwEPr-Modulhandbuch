import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-create-module-manual',
  templateUrl: './create-module-manual.component.html',
  styleUrls: ['./create-module-manual.component.scss']
})
export class CreateModuleManualComponent implements OnInit {

  visible: boolean = false; //false == form hidden | true == form visible
  spoDisabled: boolean = false;
  endDateEnabled: boolean = false;

  manualFormGroup!: FormGroup;
  spoFormGroup!: FormGroup;

  newManual!: ModuleManual;
  newSPO!: Spo;

  spos: Spo[] = [];

  constructor(private fb: FormBuilder, private restAPI: RestApiService) {
    this.initSpoForm();
    this.initManualForm();
  }

  ngOnInit(): void {
    this.restAPI.getSPOs().subscribe(resp => {
      this.spos = resp;
    });
  }

  initManualForm() {
    this.manualFormGroup = this.fb.group({
      semesterType:  new FormControl('Wintersemester'),
      semesterYears: new FormControl(''),
      spo: new FormControl('1')
    });
  }

  initSpoForm() {
    this.spoFormGroup = this.fb.group({
      id: null,
      link: new FormControl(''),
      degree: new FormControl('Bachelor'),
      course: new FormControl('Automatisierungstechnik und Robotik'),
      startDate: new FormControl(new Date(0).toISOString().split("T")[0]),
      endDate: new FormControl(new Date().toISOString().split("T")[0])
    });
  }

  onSubmit(event: {submitter:any }): void {//create new Module with form data
    /*console.log("submit");

    this.newModule = this.moduleFormGroup.value;

    this.restAPI.createModule(this.newModule).subscribe(resp => {
      console.log(resp);
    });

    this.hideDialog();
    this.resetForm();

    if(event.submitter.id=="bt-submit-new"){
      this.showDialog();
    } */
  }

  showDialog() {  //make form visible
    this.visible = true;
  }

  hideDialog() {  //hide form
    this.visible = false;
  }

  getSpoTimespan(spo: Spo) {
    if (spo.endDate == null) {
      return "ab " + new Date(spo.startDate).getFullYear()
    } else {
      return "von " + new Date(spo.startDate).getFullYear() + " bis " + new Date(spo.endDate!).getFullYear()
    }
  }

  toggleEndDate() {
    this.endDateEnabled = !this.endDateEnabled;

    if (this.endDateEnabled) {
      this.spoFormGroup.controls["endDate"].disable();
      this.spoFormGroup.controls["endDate"].reset();
    } else {
      this.spoFormGroup.controls["endDate"].enable();
      this.spoFormGroup.controls["endDate"].setValue(new Date().toISOString().split("T")[0]);
    }
  }

  toggleSpoForm() {
    this.spoDisabled = !this.spoDisabled;

    if (this.spoDisabled) {
      this.spoFormGroup.disable();
      this.updateSpoForm();
    } else {
      this.spoFormGroup.enable();
      this.initSpoForm();
    }
  }

  updateSpoForm() {
    let selectedSPO: Spo | undefined = this.spos.find(spo => {
      return spo.id === parseInt(this.manualFormGroup.controls["spo"].value)
    });

    this.spoFormGroup.controls["id"].setValue(selectedSPO!.id);
    this.spoFormGroup.controls["link"].setValue(selectedSPO!.link);
    this.spoFormGroup.controls["degree"].setValue(selectedSPO!.degree);
    this.spoFormGroup.controls["course"].setValue(selectedSPO!.course);
    this.spoFormGroup.controls["startDate"].setValue(selectedSPO!.startDate);
    this.spoFormGroup.controls["endDate"].setValue(selectedSPO!.endDate);
  }

  resetForm() {

  }
}
