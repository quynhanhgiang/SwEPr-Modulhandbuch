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

  // ### container-control ###
  rendered:boolean=false;           // true: container is visible, false: container is invisible

  // ### asynchronous data ###
  module!: Module;                  //Module by id
  id:number=0;                      //id of then module
  private routeSub!: Subscription;  //Subscription to acces url parameters
  
  /**
   * 
   * @param route ActivatedRoute to been able to acces url
   * @param restAPI rest-api for receiving Data
   */
  constructor(private route: ActivatedRoute, private restAPI: RestApiService) { }

  /**
   * initalize all Data
   */
  ngOnInit():void {
    //get id of module form url
    this.routeSub = this.route.params.subscribe(params => {
      this.id =params['id'];
    });

    //recive Manual form Rest-Api with the given id
    this.restAPI.getModule(this.id).subscribe(module => {
      this.module = module;
      this.rendered=true;
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
