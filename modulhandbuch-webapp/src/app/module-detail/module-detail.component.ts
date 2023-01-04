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
  id:number=0;

  private routeSub!: Subscription;
  constructor(private route: ActivatedRoute, private restAPI: RestApiService) { }

  ngOnInit():void {
    this.routeSub = this.route.params.subscribe(params => {
      this.id =params['id'];
    });

    this.restAPI.getModule(this.id).subscribe(module => {
      this.module = module;
      this.rendered=true;
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
