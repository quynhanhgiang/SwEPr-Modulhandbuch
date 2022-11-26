import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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

  moduleManual!: ModuleManual;
  spos: Spo[] = [];

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private restAPI: RestApiService, private fb: FormBuilder) {
    this.manualFormGroup = this.fb.group({
      id: new FormControl(''),
      semester: new FormControl(''),
      spo: this.fb.group({})
    })
  }

  ngOnInit(): void {
    let id = Number(this.activatedRoute.snapshot.paramMap.get("id"));

    this.restAPI.getModuleManual(id).subscribe(resp => {
      this.moduleManual = resp;
    });

    this.restAPI.getSPOs().subscribe(resp => {
      this.spos = resp;
    })
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
