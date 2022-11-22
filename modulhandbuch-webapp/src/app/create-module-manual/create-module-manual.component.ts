import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { Spo } from '../shared/spo';

@Component({
  selector: 'app-create-module-manual',
  templateUrl: './create-module-manual.component.html',
  styleUrls: ['./create-module-manual.component.scss']
})
export class CreateModuleManualComponent implements OnInit {
  @ViewChild('spo-form') spoFormElement!: ElementRef;

  visible: boolean = false; //false == form hidden | true == form visible
  spoDisabled: boolean = false;
  endDateEnabled: boolean = false;

  manualFormGroup!: FormGroup;
  spoFormGroup!: FormGroup;

  selectedSpo: number = 1;

  newManual!: ModuleManual;
  newSPO!: Spo;

  spos: Spo[] = [];

  constructor(private fb: FormBuilder, private restAPI: RestApiService, private router: Router) {
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
      semesterType:  new FormControl('Wintersemester', [Validators.required]),
      semesterYears: new FormControl('', [Validators.required]),
      spo: this.fb.group({})
    });
  }

  initSpoForm() {
    this.spoFormGroup = this.fb.group({
      id: null,
      link: new FormControl('', [Validators.required]),
      degree: new FormControl('Bachelor', [Validators.required]),
      course: new FormControl('Automatisierungstechnik und Robotik', [Validators.required]),
      startDate: new FormControl(new Date(0).toISOString().split("T")[0], [Validators.required]),
      endDate: new FormControl(new Date().toISOString().split("T")[0])
    });
  }

  formsValid(): boolean {
    return (this.spoFormGroup.valid || this.spoDisabled) && this.manualFormGroup.valid;
  }

  save(reset: boolean, open: boolean) {
    if (this.formsValid()) {
      if (!this.spoDisabled) {
        console.log(this.spoFormGroup.getRawValue());

        this.restAPI.createSPO(this.spoFormGroup.getRawValue()).subscribe(resp => {
          let newMan: ModuleManual = {
            id: null,
            semester: this.manualFormGroup.controls["semesterType"].value + " " + this.manualFormGroup.controls["semesterYears"].value,
            spo: resp
          }

          console.log(newMan);
          this.restAPI.createModuleManual(newMan).subscribe(resp => {
            if (reset) {
              this.resetForm();
              return;
            }

            if (open) {
              this.router.navigate(['/module-manual-detail-view', resp.id]);
              return;
            }

            this.hideDialog();
          });
        });
      }
      else {
        let newMan: ModuleManual = {
          id: null,
          semester: this.manualFormGroup.controls["semesterType"].value + " " + this.manualFormGroup.controls["semesterYears"].value,
          spo: this.spos.find(spo => {
            return spo.id === this.selectedSpo;
          })!
        }

        console.log(newMan);
        this.restAPI.createModuleManual(newMan).subscribe(resp => {
          if (reset) {
            this.resetForm();
            return;
          }

          if (open) {
            this.router.navigate(['/module-manual-detail-view', resp.id]);
            return;
          }

          this.hideDialog();
        });
      }
    } else {
      alert("Bitte füllen Sie alle Felder (ggf. mit Ausnahme des Gültigkeits-Endes) aus!");
    }
  }

  showDialog() {  //make form visible
    this.spoFormGroup.enable();
    this.initSpoForm();
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
      this.updateSpoForm("-1");
    } else {
      this.spoFormGroup.enable();
      this.initSpoForm();
    }
  }

  updateSpoForm(id: string) {
    if (parseInt(id) > 0) {
      this.selectedSpo = parseInt(id);
    }

    let selectedSPO: Spo | undefined = this.spos.find(spo => {
      return spo.id === this.selectedSpo;
    });

    this.spoFormGroup.controls["id"].setValue(selectedSPO!.id);
    this.spoFormGroup.controls["link"].setValue(selectedSPO!.link);
    this.spoFormGroup.controls["degree"].setValue(selectedSPO!.degree);
    this.spoFormGroup.controls["course"].setValue(selectedSPO!.course);
    this.spoFormGroup.controls["startDate"].setValue(selectedSPO!.startDate);
    this.spoFormGroup.controls["endDate"].setValue(selectedSPO!.endDate);
  }

  resetForm() {
    this.initManualForm();
    this.initSpoForm();

    this.spoDisabled = false;
    this.endDateEnabled = false;
  }
}
