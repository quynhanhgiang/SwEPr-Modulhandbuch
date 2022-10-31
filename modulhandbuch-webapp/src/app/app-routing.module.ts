import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyComponent } from './dummy/dummy.component';

const routes: Routes = [
  { path: 'home', component: DummyComponent },
  { path: 'module-manuals', component: DummyComponent },
  { path: 'module-management', component: DummyComponent },
  { path: 'user-management', component: DummyComponent },
  { path: 'placeholder1', component: DummyComponent },
  { path: 'placeholder2', component: DummyComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
