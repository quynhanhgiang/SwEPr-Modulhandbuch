import { Component, ElementRef, Host, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GetModuleManualsComponent } from '../get-module-manuals/get-module-manuals.component';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-create-module-manual',
  templateUrl: './create-module-manual.component.html',
  styleUrls: ['./create-module-manual.component.scss'],
})
export class CreateModuleManualComponent implements OnInit {

  // ### dialog-control ###
  dialogVisible: boolean = false; // true: dialog is visible, false: dialog is invisible

  // ### form-groups ###
  manualFormGroup!: FormGroup;    // main-formgroup
  spoFormGroup!: FormGroup;       // sub-formgroup

  // ### form-controls ###
  selectedSpoIndex: string = "0"; // the index of the currently selected spo in spos-list; binded to spo-select
  spoDisabled: boolean = false;   // true: spo-form-group is disabled & spo-selct enabled, false: vice versa; binded to spo-select-checkbox
  endDateEnabled: boolean = true; // true: end-date-picker enabled, false: end-date-picker disabled (null)

  // ### asynchronous data ###
  spos: Spo[] = [];               //spos for spo-select

  constructor(@Host() private getManualsComp: GetModuleManualsComponent, private fb: FormBuilder, private restAPI: RestApiService, private router: Router) {
    this.initFormGroups();
  }

  ngOnInit(): void {
    this.restAPI.getSPOs().subscribe((resp) => {
     this.spos = resp;
    });
  }

  /**
   * Initializes the spo-formgroup and the manual-formgroup.
   */
  initFormGroups() {
    this.spoFormGroup = this.fb.group({
      id: null,
      link: new FormControl('', [Validators.required]),
      degree: new FormControl('Bachelor', [Validators.required]),
      course: new FormControl('', [Validators.required]),
      startDate: new FormControl(new Date(0).toISOString().split('T')[0], [Validators.required]),
      endDate: new FormControl(new Date().toISOString().split('T')[0]),
    });

    this.manualFormGroup = this.fb.group({
      semesterType: new FormControl('Wintersemester', [Validators.required]),
      semesterYears: new FormControl('', [Validators.required]),
      spoGroup: this.spoFormGroup
    });
  }

  /**
   * Resets the whole form.
   */
  resetForm() {
    this.initFormGroups();

    this.spoDisabled = false;
    this.endDateEnabled = true;
    this.selectedSpoIndex = "0";
  }

  /**
   * Only Resets the spo-formgroup (sub-formgroup of manual-formgroup).
   */
  resetSpoForm() {
    this.spoFormGroup.enable();
    this.spoFormGroup.controls["id"].setValue(null);
    this.spoFormGroup.controls["link"].setValue("");
    this.spoFormGroup.controls["degree"].setValue("Bachelor");
    this.spoFormGroup.controls["course"].setValue("");
    this.spoFormGroup.controls["startDate"].setValue(new Date(0).toISOString().split('T')[0]);
    this.spoFormGroup.controls["endDate"].setValue(new Date().toISOString().split('T')[0]);
  }

  /**
   * Updates all form-controls of spo-formgroup with the values of the currently selected spo.
   * @param index Index of the selected spo in spos-list.
   */
  updateSpoForm(index: string) {
    this.selectedSpoIndex = index;
    let selectedSPO: Spo = this.spos[parseInt(this.selectedSpoIndex)];

    this.spoFormGroup.controls['id'].setValue(selectedSPO!.id);
    this.spoFormGroup.controls['link'].setValue(selectedSPO!.link);
    this.spoFormGroup.controls['degree'].setValue(selectedSPO!.degree);
    this.spoFormGroup.controls['course'].setValue(selectedSPO!.course);
    this.spoFormGroup.controls['startDate'].setValue(selectedSPO!.startDate);
    this.spoFormGroup.controls['endDate'].setValue(selectedSPO!.endDate);
  }

  /**
   * Submits the form. -> Calls API to create a new module-manual (and in certain circumstances a new spo).
   * @param event Event that triggered the submit. Needed to find out, which button was clicked.
   */
  onSubmit(event: {submitter: any}) {

    let newManual: ModuleManual = {
      id: null,
      semester: this.manualFormGroup.controls['semesterType'].value + ' ' + this.manualFormGroup.controls['semesterYears'].value,
      spo: (this.spoDisabled) ?  this.spos[parseInt(this.selectedSpoIndex)]  : this.manualFormGroup.controls['spoGroup'].getRawValue()
    }

    this.restAPI.createModuleManual(newManual).subscribe((resp) => {
      this.getManualsComp.moduleManuals.push(resp);

      if(event.submitter.id=="bt-submit-open"){
        this.router.navigate(['/module-manual-detail-view', resp.id]);
        return;
      }

      if(event.submitter.id=="bt-submit-new"){
        this.resetForm();
        return;
      }

      this.hideDialog();
    });
  }

  /**
   * Toggles the disabled-property of the spo-form and handles its contents.
   */
  toggleSpoForm() {
    this.spoDisabled = !this.spoDisabled;

    if (this.spoDisabled) {
      this.spoFormGroup.disable();
      this.updateSpoForm(this.selectedSpoIndex);
      this.endDateEnabled = false;
      return;
    }

    this.spoFormGroup.enable();
    this.resetSpoForm();
    this.endDateEnabled = true;
  }

  /**
   * Toggles the disabled-property of the end-date-picker and handles its contents.
   */
  toggleEndDate() {
    this.endDateEnabled = !this.endDateEnabled;

    if (this.endDateEnabled) {
      this.spoFormGroup.controls['endDate'].enable();
      this.spoFormGroup.controls['endDate'].setValue(
        new Date().toISOString().split('T')[0]
      );
      return;
    }

    this.spoFormGroup.controls['endDate'].disable();
    this.spoFormGroup.controls['endDate'].reset();
  }

  /**
   * Helper-function to clearly display the validity-period of a spo.
   * @param spo
   */
  getSpoTimespan(spo: Spo) {
    if (spo.endDate == null) {
      return 'ab ' + new Date(spo.startDate).getFullYear();
    }

    return 'von ' + new Date(spo.startDate).getFullYear() + ' bis ' + new Date(spo.endDate!).getFullYear();
  }

   /**
   * Makes the create-manual-dialog visible.
   */
    showDialog() {
      this.dialogVisible = true;
    }

    /**
     * Hides the create-manual-dialog.
     */
    hideDialog() {
      this.dialogVisible = false;
    }
}
