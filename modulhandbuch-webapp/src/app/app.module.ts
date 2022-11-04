import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MenubarComponent } from './menubar/menubar.component';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';

import {TableModule} from 'primeng/table';
import { ModuleService } from './get-modules/moduleService';
import {FilterService} from 'primeng/api';
import {DialogModule} from 'primeng/dialog';
import {EditorModule} from 'primeng/editor';
import { CreateModuleComponent } from './create-module/create-module.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    MenubarComponent,
    DummyComponent,
    GetModulesComponent,
    CreateModuleComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TableModule,
    BrowserAnimationsModule,
    DialogModule,
    FormsModule,
    EditorModule,
    ReactiveFormsModule
  ],
  providers: [
    ModuleService,
    FilterService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
