import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { FileStatus } from '../shared/FileStatus';
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
  firstPageStatus: FileStatus = {filename: null, link: null, timestamp: null};
  modulePlanFile: File | null = null;
  modulePlanStatus: FileStatus = {filename: null, link: null, timestamp: null};
  preliminaryNoteFile: File | null = null;
  preliminaryNoteStatus: FileStatus = {filename: null, link: null, timestamp: null};

  firstFormSuccess: boolean = false;
  thirdFormSuccess: boolean = false;

  moduleManual!: ModuleManual;
  spos: Spo[] = [];

  studyphases: string[] = [];
  moduletypes: string[] = [];
  requirements: string[] = [];

  constructor(private activatedRoute: ActivatedRoute, private restAPI: RestApiService, private fb: FormBuilder) {
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
    });

    this.restAPI.getFirstPageStatus(id).subscribe(status => this.firstPageStatus = status);
    this.restAPI.getModulePlanStatus(id).subscribe(status => this.modulePlanStatus = status);
    this.restAPI.getPreliminaryNoteStatus(id).subscribe(status => this.preliminaryNoteStatus = status);

    this.restAPI.getSegments(id).subscribe(segments => this.studyphases = segments);
    this.restAPI.getModuleTypes(id).subscribe(moduletypes => this.moduletypes = moduletypes);
    this.restAPI.getRequirements(id).subscribe(requirements => this.requirements = requirements);
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
  submitFirstPage() {
    if (this.firstPageFile == null)
      return;

    this.restAPI.uploadFirstPage(this.moduleManual.id!, this.firstPageFile).subscribe(status => this.firstPageStatus = status);
  }

  /**
   * Submits the second form (files-section).
   */
  submitModulePlan() {
    if (this.modulePlanFile == null)
      return;

    this.restAPI.uploadModulePlan(this.moduleManual.id!, this.modulePlanFile).subscribe(status => this.modulePlanStatus = status);
  }

  /**
   * Submits the second form (files-section).
   */
  submitPreliminaryNote() {
    if (this.preliminaryNoteFile == null)
      return;

    this.restAPI.uploadPreliminaryNote(this.moduleManual.id!, this.preliminaryNoteFile).subscribe(status => this.preliminaryNoteStatus = status);
  }

  /**
   * Submits the third form (assignments-section).
   */
  submitAssignments() {
    this.restAPI.updateSegments(this.moduleManual.id!, this.studyphases).subscribe(r1 => {
      this.restAPI.updateModuleTypes(this.moduleManual.id!, this.moduletypes).subscribe(r2 => {
        this.restAPI.updateRequirements(this.moduleManual.id!, this.requirements).subscribe(r3 => {
          this.thirdFormSuccess = true
          setTimeout(() => this.thirdFormSuccess = false, 2000);
        });
      });
    });
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
