import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';
import { GetModuleManualsComponent } from './get-module-manuals/get-module-manuals.component';
import { ModuleDetailComponent } from './module-detail/module-detail.component';

const routes: Routes = [
  { path: 'home', component: DummyComponent },
  { path: 'module-manuals', component: GetModuleManualsComponent },
  { path: 'module-management', component: GetModulesComponent },
  { path: 'module-detail-view/:id', component: ModuleDetailComponent },
  { path: 'user-management', component: DummyComponent },
  { path: 'module-manual-detail-view/:id', component: DummyComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }