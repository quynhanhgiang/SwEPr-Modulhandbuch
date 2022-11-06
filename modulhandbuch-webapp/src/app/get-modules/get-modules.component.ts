import { Component, OnInit } from '@angular/core';
import { FilterMatchMode, FilterService, SelectItem } from 'primeng/api';
import { RestApiService } from '../services/rest-api.service';
import { FlatModule } from '../shared/module';

@Component({
  selector: 'app-get-modules',
  templateUrl: './get-modules.component.html',
  styleUrls: ['./get-modules.component.scss']
})

export class GetModulesComponent implements OnInit {

  matchModeOptions!: SelectItem[];
  
  constructor(private restAPI: RestApiService) { }

  selectedModule!:FlatModule;

  modules!: FlatModule[];

  ngOnInit(): void {
    this.restAPI.getModules().subscribe(modules => {
      this.modules = modules;
    });

    this.matchModeOptions = [
      { label: "beginnt mit", value: FilterMatchMode.STARTS_WITH },
      { label: "beinhaltet", value: FilterMatchMode.CONTAINS },
      { label: "endet mit", value: FilterMatchMode.ENDS_WITH },
      { label: "entspricht nicht", value: FilterMatchMode.NOT_EQUALS },
      { label: "beinhaltet nicht", value: FilterMatchMode.NOT_CONTAINS },
    ];
  }
}