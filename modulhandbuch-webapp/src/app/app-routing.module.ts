import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';
import { GetModuleManualsComponent } from './get-module-manuals/get-module-manuals.component';
import { ModuleDetailComponent } from './module-detail/module-detail.component';
import { EditManualComponent } from './edit-manual/edit-manual.component';
import { EditManualGeneralComponent } from './edit-manual-general/edit-manual-general.component';
import { EditManualModulesComponent } from './edit-manual-modules/edit-manual-modules.component';

const routes: Routes = [
  {
    path: 'home',
    component: DummyComponent
  },
  {
    path: 'module-manuals',
    component: GetModuleManualsComponent
  },
  {
    path: 'module-management',
    component: GetModulesComponent
  },
  {
    path: 'module-detail-view/:id',
    component: ModuleDetailComponent
  },
  {
    path: 'user-management',
    component: DummyComponent
  },
  {
    path: 'manual-edit/:id',
    component: EditManualComponent,
    children: [
      {
        path: 'general',
        component: EditManualGeneralComponent
      },
      {
        path: 'modules',
        component: EditManualModulesComponent
      },
      {
        path: '',
        redirectTo: 'general',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
