import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MenubarComponent } from './menubar/menubar.component';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';
import { CreateModuleComponent } from './create-module/create-module.component';
import { SanitizeHtmlPipe } from './module-detail/SanitizeHtmlPipe';
import { GetModuleManualsComponent } from './get-module-manuals/get-module-manuals.component';
import { ModuleDetailComponent } from './module-detail/module-detail.component';
import { EditModuleComponent } from './edit-module/edit-module.component';

import { TableModule } from 'primeng/table';
import { FilterService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { EditorModule } from 'primeng/editor';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { DataViewModule } from 'primeng/dataview';
import { InputTextModule } from 'primeng/inputtext';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {PickListModule} from 'primeng/picklist';

import {ConfirmationService} from 'primeng/api';
import { RestApiService } from './services/rest-api.service';

import { CreateModuleManualComponent } from './create-module-manual/create-module-manual.component';
import { GetCollegeEmployeesComponent } from './get-college-employees/get-college-employees.component';
import { CreateCollegeEmployeeComponent } from './create-college-employee/create-college-employee.component';
import { EditManualComponent } from './edit-manual/edit-manual.component';
import { EditManualGeneralComponent } from './edit-manual-general/edit-manual-general.component';
import { EditManualModulesComponent } from './edit-manual-modules/edit-manual-modules.component';
import { DynamicListComponent } from './dynamic-list/dynamic-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    MenubarComponent,
    DummyComponent,
    GetModulesComponent,
    CreateModuleComponent,
    GetModuleManualsComponent,
    ModuleDetailComponent,
    EditModuleComponent,
    SanitizeHtmlPipe,
    CreateModuleManualComponent,
    GetCollegeEmployeesComponent,
    CreateCollegeEmployeeComponent,
    EditManualComponent,
    EditManualGeneralComponent,
    EditManualModulesComponent,
    DynamicListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TableModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DialogModule,
    EditorModule,
    FormsModule,
    ReactiveFormsModule,
    MultiSelectModule,
    DropdownModule,
    DataViewModule,
    InputTextModule,
    ConfirmDialogModule,
    PickListModule
  ],
  providers: [
    FilterService,
    RestApiService,
    ConfirmationService,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
