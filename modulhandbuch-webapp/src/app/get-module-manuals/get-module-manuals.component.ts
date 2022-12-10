import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api/selectitem';
import { RestApiService } from '../services/rest-api.service';
import { ModuleManual } from '../shared/module-manual';
import { moduleManuals } from './mock-module-manuals';

@Component({
  selector: 'app-get-module-manuals',
  templateUrl: './get-module-manuals.component.html',
  styleUrls: ['./get-module-manuals.component.scss']
})
export class GetModuleManualsComponent implements OnInit {

  moduleManuals: ModuleManual[] = [];

  sortOptions: SelectItem[] = [];
  sortKey: string = "";
  sortField: string = "";
  sortOrder: number = 1;

  emptyMessage = "Modulhandbücher werden geladen..."

  constructor(private restAPI: RestApiService) { }

  ngOnInit(): void {
    this.restAPI.getModuleManuals().subscribe(resp => {
      this.moduleManuals = resp;

      if (resp.length === 0) {
        this.emptyMessage = "Es wurden noch keine Modulhandbücher angelegt. Zum Anlegen bitte auf 'Neues Modulhandbuch' klicken."
      } else {
        this.emptyMessage = "Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe und stellen Sie sicher, dass lediglich das Studienfach gesucht wurde (z.B. 'Visual Computing')."
      }
    });

    this.sortOptions = [
      {label: 'Gültigkeit aufsteigend', value: 'spo.startDate'},
      {label: 'Gültigkeit absteigend', value: '!spo.startDate'}
    ];
  }

  getGermanDate(isoDate: string): string {
    let d: Date = new Date(isoDate);
    return d.toLocaleDateString("de", {timeZone:'Europe/Berlin', year: 'numeric', month: 'long', day: 'numeric' })
  }

  onSortChange(select: HTMLInputElement) {
    let value = select.value;

    console.log(value);

    if (value.indexOf('!') === 0) {
        this.sortOrder = -1;
        this.sortField = value.substring(1, value.length);
    }
    else {
        this.sortOrder = 1;
        this.sortField = value;
    }
}
}
