import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-edit-manual-general',
  templateUrl: './edit-manual-general.component.html',
  styleUrls: ['./edit-manual-general.component.scss']
})
export class EditManualGeneralComponent implements OnInit {

  manualFormGroup: FormGroup;
  spoFormGroup: FormGroup;

  selectedSpoIndex: number | null = null;

  firstPageFile: File | null = null;
  preliminaryNoteFile: File | null = null;
  modulePlanFile: File | null = null;

  firstFormSuccess: boolean = false;
  secondFormSuccess: boolean = false;
  thirdFormSuccess: boolean = false;

  moduleManual!: ModuleManual;
  spos: Spo[] = [];

  studyphases: string[] = [];
  moduletypes: string[] = [];
  requirements: string[] = [];

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private restAPI: RestApiService, private fb: FormBuilder) {
    this.spoFormGroup = this.fb.group({
      id: [{value: ''}],
      link: [{value: '', disabled: true}],
      degree: [{value: '', disabled: true}],
      course: [{value: '', disabled: true}],
      startDate: [{value: '', disabled: true}],
      endDate: [{value: '', disabled: true}],
    });

    this.manualFormGroup = this.fb.group({
      id: [{value: ''}],
      semester: [{value: ''}, Validators.required],
      spo: this.spoFormGroup
    })
  }

  ngOnInit(): void {
    let id = Number(this.activatedRoute.snapshot.parent!.paramMap.get("id"));

    this.restAPI.getSPOs().subscribe(spos => {
      this.spos = spos;

      this.restAPI.getModuleManual(id).subscribe(manual => {
        this.moduleManual = manual;
        this.manualFormGroup.setValue(manual);

        this.selectedSpoIndex = spos.map(e => e.id).indexOf(manual.spo.id);
      });
    })
  }

  /**
   * Updates all form-controls of spo-formgroup with the values of the currently selected spo.
   * @param index Index of the selected spo in spos-list.
   */
   updateSpoForm(index: string) {
    this.selectedSpoIndex = Number(index);
    this.spoFormGroup.setValue(this.spos[this.selectedSpoIndex]);
  }

  /**
   * Submits the first form (manual + spo) -> updates the module-manual.
   */
  submitManual() {
    this.moduleManual = this.manualFormGroup.getRawValue();

    this.restAPI.updateModuleManual(this.moduleManual).subscribe(manual => {
      this.moduleManual = manual;

      this.firstFormSuccess = true
      setTimeout(() => this.firstFormSuccess = false, 2000);
    });
  }

  handleFileInput(files: FileList, file: File | null) {
    file = files.item(0);
  }

  /**
   * Submits the second form (files-section).
   */
  submitFiles() {

  }

  /**
   * Submits the third form (assignments-section).
   */
  submitAssignments() {

  }

  /**
   * Helper-function to clearly display the validity-period of a spo.
   * @param spo
   */
   getSpoTimespan(spo: Spo): string {
    if (spo.endDate == null) {
      return 'ab ' + new Date(spo.startDate).getFullYear();
    }

    return 'von ' + new Date(spo.startDate).getFullYear() + ' bis ' + new Date(spo.endDate!).getFullYear();
  }

}
