import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { Assignment } from '../shared/Assignments';
import { ManualVariation } from '../shared/ManualVariation';
import { ModuleManual } from '../shared/module-manual';

@Component({
  selector: 'app-edit-manual-modules',
  templateUrl: './edit-manual-modules.component.html',
  styleUrls: ['./edit-manual-modules.component.scss']
})
export class EditManualModulesComponent implements OnInit {
  @Input() id: number = 0;

  editDialogVisible = false;
  submitSuccess: boolean = false;

  variationToEdit!: ManualVariation;
  variationFormGroup: FormGroup;

  moduleManual!: ModuleManual;

  unassignedModules: ManualVariation[] = [];
  assignedModules: ManualVariation[] = [];

  segments: Assignment[] = [];
  moduleTypes: Assignment[] = [];
  requirements: Assignment[] = [];

  constructor(private fb: FormBuilder, private router: Router, private restAPI: RestApiService) {
    this.variationFormGroup = this.fb.group({
      module: {},
      semester: ['', [Validators.required, Validators.min(1), Validators.max(7)]],
      segment: [{}, [Validators.required]],
      moduleType: [{}, [Validators.required]],
      sws: ['', [Validators.required, Validators.min(1), Validators.max(40)]],
      ects: ['', [Validators.required, Validators.min(1), Validators.max(30)]],
      workLoad: ['', [Validators.required]],
      admissionRequirement: [{}, [Validators.required]],
      isAssigned: false
    });
  }

  ngOnInit(): void {

    this.restAPI.getModuleManual(this.id).subscribe(manual => {
      this.moduleManual = manual;
    });

    this.restAPI.getModulesAssignableTo(this.id).subscribe(modules => {
      for (let mod of modules) {
        this.unassignedModules.push({
          module: mod,
          semester: null,
          sws: null,
          ects: null,
          workLoad: null,
          moduleType: null,
          segment: null,
          admissionRequirement: null,
          isAssigned: false
        });
      }

      this.unassignedModules.sort( this.compareVariations );
    });

    this.restAPI.getAssignedModules(this.id).subscribe(modules => {
      for(let mod of modules) {
        mod.isAssigned = true;
      }

      this.assignedModules = modules;
      this.assignedModules.sort( this.compareVariations );
    });

    this.restAPI.getSegments(this.id).subscribe(segments => {this.segments = segments});
    this.restAPI.getModuleTypes(this.id).subscribe(types => {this.moduleTypes = types});
    this.restAPI.getRequirements(this.id).subscribe(requirements => {this.requirements = requirements});
  }

  /**
   * Method is called, after trying to assign one or multiple modules. Checks for each module-assignment, if its valid.
   * Unvaild module-assignments are rejected. Sets valid-flag, if assignment is valid.
   * @param itemTransferEvent Event with item-list containing the assigned moduleVariation-objects.
   */
  assignModule(itemTransferEvent: any) {
    for (let manualVar of itemTransferEvent.items) {
      if (!this.isValidVariation(manualVar)) {
        this.assignedModules.splice(this.assignedModules.indexOf(manualVar, 0), 1);
        this.unassignedModules.push(manualVar);
        continue;
      }
      manualVar.isAssigned = true;
    }

    this.unassignedModules.sort( this.compareVariations );
    this.assignedModules.sort( this.compareVariations );
  }

  /**
   * Method is called, after trying to unassign one ore multiple modules. Unsets valid-flag for each unassigned module.
   * @param itemTransferEvent Event with item-list containing the unassigned moduleVariation-objects.
   */
  unassignModule(itemTransferEvent: any) {
    for (let manualVar of itemTransferEvent.items) {
      manualVar.isAssigned = false;
    }

    this.unassignedModules.sort( this.compareVariations );
  }

  /**
   * Shows the edit-dialog for the specified manualVariation-object.
   * @param manualVar manualVariation to edit
   */
  editManualVar(manualVar: ManualVariation) {
    this.variationToEdit = manualVar;
    this.variationFormGroup.patchValue(manualVar);
    this.variationFormGroup.controls["segment"].setValue(this.segments.find(item => item.id == manualVar.segment?.id) ?? null);
    this.variationFormGroup.controls["moduleType"].setValue(this.moduleTypes.find(item => item.id == manualVar.moduleType?.id) ?? null);
    this.variationFormGroup.controls["admissionRequirement"].setValue(this.requirements.find(item => item.id == manualVar.admissionRequirement?.id) ?? null);

    this.editDialogVisible = true;
  }

  /**
   * Reset and close the eddit-dialog.
   */
  closeDialog() {
    this.variationFormGroup.reset();
    this.editDialogVisible = false;
  }

  /**
   * Save the changes made to the manualVariation and close the dialog.
   */
  saveVariationChanges() {
    if (!this.variationFormGroup.valid) {
      return;
    }

    this.variationToEdit.semester = this.variationFormGroup.controls["semester"].value;
    this.variationToEdit.segment = this.variationFormGroup.controls["segment"].value;
    this.variationToEdit.moduleType = this.variationFormGroup.controls["moduleType"].value;
    this.variationToEdit.sws = this.variationFormGroup.controls["sws"].value;
    this.variationToEdit.ects = this.variationFormGroup.controls["ects"].value;
    this.variationToEdit.workLoad = this.variationFormGroup.controls["workLoad"].value;
    this.variationToEdit.admissionRequirement = this.variationFormGroup.controls["admissionRequirement"].value;

    this.closeDialog();
  }

  /**
   * Submits the assignedModules-List.
   * @param close if true, the webapp returns to the module-manual-overview
   */
  submit(close: boolean) {
    this.restAPI.updateAssignedModules(this.moduleManual.id!, this.assignedModules).subscribe(resp => {
      if (close) {
        this.router.navigate(['/module-manuals']);
        return;
      }

      this.submitSuccess = true
      setTimeout(() => this.submitSuccess = false, 2000);
    });
  }

  /**
   * Comparator-Function for comparisons between two ManualVariation-Objects.
   * @param v1
   * @param v2
   * @returns
   */
  compareVariations(v1: ManualVariation, v2: ManualVariation) {
    if ( v1.module.moduleName + v1.module.moduleOwner < v2.module.moduleName + v2.module.moduleOwner ){
      return -1;
    }
    return 1;
  }

  /**
   * Returns wheter a given manualVariation is valid or not. Unvalid manualVariations should not be moved to assignedModules-List.
   * @param manualVar the object whose validity is to be checked
   * @returns true, if valid, false otherwise
   */
  isValidVariation(manualVar: ManualVariation): boolean {
    let {admissionRequirement, ...reducedVar} = manualVar;

    return Object.values(reducedVar).every(val => val !== null);
  }
}


