import { Routes } from '@angular/router';
import { LoginComponent } from './Components/Login/Login';
import { DashboardComponent } from './Components/Dashboard/Dashboard';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: '' }
];
