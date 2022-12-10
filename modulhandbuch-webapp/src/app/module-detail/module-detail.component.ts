import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { RestApiService } from '../services/rest-api.service';
import { Module } from '../shared/module';

@Component({
  selector: 'app-module-detail',
  templateUrl: './module-detail.component.html',
  styleUrls: ['./module-detail.component.scss']
})

export class ModuleDetailComponent implements OnInit {

  module!: Module;
  rendered:boolean=false;

  private routeSub!: Subscription;
  constructor(private route: ActivatedRoute, private restAPI: RestApiService) { }

  ngOnInit():void {
    let id=0;
    this.routeSub = this.route.params.subscribe(params => {
      id =params['id'];
    });

    this.restAPI.getModule(id).subscribe(module => {
      this.module = module;
      this.rendered=true;
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
