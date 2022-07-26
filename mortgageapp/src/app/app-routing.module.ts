import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { AppComponent } from './app.component';
import { MortgageComponent } from './mortgage-display.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'show', component: MortgageComponent },

  // otherwise redirect to home
  //{ path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { };
export const routingComponents = [HomeComponent, AppComponent, MortgageComponent]