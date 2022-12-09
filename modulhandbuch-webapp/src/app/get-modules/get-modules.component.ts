import { Component, OnInit } from '@angular/core';
import { FilterMatchMode, FilterService, SelectItem } from 'primeng/api';
import { RestApiService } from '../services/rest-api.service';
import { FlatModule } from '../shared/FlatModule';

@Component({
  selector: 'app-get-modules',
  templateUrl: './get-modules.component.html',
  styleUrls: ['./get-modules.component.scss']
})

export class GetModulesComponent implements OnInit {
  
  matchModeOptions!: SelectItem[];
  selectedModule!:FlatModule;
  message:string ="Module werden geladen...";
  modules: FlatModule[]=[];
  
  constructor(private restAPI: RestApiService) { }

  ngOnInit(): void {
    this.restAPI.getModulesOverview().subscribe(modules => {
      this.modules = modules;
      
      if(this.modules.length>0){
        this.message = "Keine Ergebnisse gefunden. Bitte überprüfen Sie die Korrektheit der Eingabe."
      }else{
        this.message = "Es wurden noch keine Module angelegt. Zum Anlegen bitte auf 'Neues Modul erstellen' klicken."
      }
    });


    this.matchModeOptions = [//translate column filter-names to german
      { label: "beginnt mit", value: FilterMatchMode.STARTS_WITH },
      { label: "beinhaltet", value: FilterMatchMode.CONTAINS },
      { label: "endet mit", value: FilterMatchMode.ENDS_WITH },
      { label: "entspricht nicht", value: FilterMatchMode.NOT_EQUALS },
      { label: "beinhaltet nicht", value: FilterMatchMode.NOT_CONTAINS },
    ];
  }
  
}