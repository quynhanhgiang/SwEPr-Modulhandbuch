import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { ManualVariation } from '../shared/ManualVariation';
import { ModuleManual } from '../shared/module-manual';
import { assignableModules, assignedModules } from './mock-modules';

@Component({
  selector: 'app-edit-manual-modules',
  templateUrl: './edit-manual-modules.component.html',
  styleUrls: ['./edit-manual-modules.component.scss']
})
export class EditManualModulesComponent implements OnInit {

  submitSuccess: boolean = false;

  moduleManual!: ModuleManual;

  unassignedModules: ManualVariation[] = [];
  assignedModules: ManualVariation[] = [];

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private restAPI: RestApiService) { }

  ngOnInit(): void {
    let id = Number(this.activatedRoute.snapshot.parent!.paramMap.get("id"));

    this.restAPI.getModuleManual(id).subscribe(manual => {
      this.moduleManual = manual;
    });

    this.assignedModules = assignedModules;

    for (let mod of assignableModules) {
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

    this.assignedModules.sort( this.compareVariations );
    this.unassignedModules.sort( this.compareVariations );
  }

  /**
   *
   * @param itemTransferEvent
   */
  assignModule(itemTransferEvent: any) {
    let manualVar = itemTransferEvent.items[0];

    if (!this.isValidVariation(manualVar)) {
      this.assignedModules.splice(this.assignedModules.indexOf(manualVar, 0), 1);
      this.unassignedModules.push(manualVar);
      this.unassignedModules.sort( this.compareVariations );
      return;
    }

    manualVar.isAssigned = true;
    this.assignedModules.sort( this.compareVariations );
  }

  /**
   *
   * @param itemTransferEvent
   */
  unassignModule(itemTransferEvent: any) {
    itemTransferEvent.items[0].isAssigned = false;
    this.unassignedModules.sort( this.compareVariations );
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
  private compareVariations(v1: ManualVariation, v2: ManualVariation) {
    if ( v1.module.moduleName + v1.module.moduleOwner < v2.module.moduleName + v2.module.moduleOwner ){
      return -1;
    }
    if ( v1.module.moduleName + v1.module.moduleOwner > v2.module.moduleName + v2.module.moduleOwner){
      return 1;
    }
    return 0;
  }

  /**
   * Returns wheter a given manualVariation is valid or not. Unvalid manualVariations should not be moved to assignedModules-List.
   * @param manualVar the object whose validity is to be checked
   * @returns true, if valid, false otherwise
   */
  private isValidVariation(manualVar: ManualVariation): boolean {
    return  manualVar.semester != null && manualVar.semester > 0 && manualVar.semester < 10 &&
            manualVar.sws != null && manualVar.sws > 0 && manualVar.sws < 30 &&
            manualVar.ects != null && manualVar.ects > 0 && manualVar.ects < 100 &&
            manualVar.moduleType != null &&
            manualVar.segment != null &&
            manualVar.workLoad != null &&
            manualVar.admissionRequirement != null;
  }
}


