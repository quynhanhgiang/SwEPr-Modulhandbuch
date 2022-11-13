import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyComponent } from './dummy/dummy.component';
import { GetModulesComponent } from './get-modules/get-modules.component';

const routes: Routes = [
  { path: 'home', component: DummyComponent },
  { path: 'module-manuals', component: DummyComponent },
  { path: 'module-management', component: GetModulesComponent },
  { path: 'user-management', component: DummyComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }