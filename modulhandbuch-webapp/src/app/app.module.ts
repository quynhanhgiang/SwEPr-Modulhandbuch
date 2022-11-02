import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MenubarComponent } from './menubar/menubar.component';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';

import {TableModule} from 'primeng/table';
import { ModuleService } from './get-modules/moduleService';
import {FilterService} from 'primeng/api';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    MenubarComponent,
    DummyComponent,
    GetModulesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TableModule,
    BrowserAnimationsModule
  ],
  providers: [
    ModuleService,
    FilterService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
