import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { roleGuard } from './guards/role.guard';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RegisterClienteComponent } from './components/register-cliente/register-cliente.component';
import { VehiculosListComponent } from './components/vehiculos/vehiculos-list/vehiculos-list.component';
import { VehiculoFormComponent } from './components/vehiculos/vehiculo-form/vehiculo-form.component';
import { MantenimientosListComponent } from './components/mantenimientos/mantenimientos-list/mantenimientos-list.component';
import { MantenimientoFormComponent } from './components/mantenimientos/mantenimiento-form/mantenimiento-form.component';
import { ClienteVehiculosComponent } from './components/cliente/cliente-vehiculos/cliente-vehiculos.component';

export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register/:role', component: RegisterComponent },
  
  // Rutas de Administrador
  { 
    path: 'admin/vehiculos', 
    component: VehiculosListComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/vehiculos/nuevo', 
    component: VehiculoFormComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/vehiculos/editar/:id', 
    component: VehiculoFormComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/mantenimientos', 
    component: MantenimientosListComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/mantenimientos/nuevo', 
    component: MantenimientoFormComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/mantenimientos/editar/:id', 
    component: MantenimientoFormComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/mantenimientos/vehiculo/:idVehiculo', 
    component: MantenimientosListComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  { 
    path: 'admin/register-cliente', 
    component: RegisterClienteComponent, 
    canActivate: [authGuard, roleGuard(['admin'])] 
  },
  
  // Rutas de Empleado
  { 
    path: 'empleado/vehiculos', 
    component: VehiculosListComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/vehiculos/nuevo', 
    component: VehiculoFormComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/vehiculos/editar/:id', 
    component: VehiculoFormComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/mantenimientos', 
    component: MantenimientosListComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/mantenimientos/nuevo', 
    component: MantenimientoFormComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/mantenimientos/editar/:id', 
    component: MantenimientoFormComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  { 
    path: 'empleado/mantenimientos/vehiculo/:idVehiculo', 
    component: MantenimientosListComponent, 
    canActivate: [authGuard, roleGuard(['empleado'])] 
  },
  
  // Rutas de Cliente (solo lectura)
  { 
    path: 'cliente/vehiculos', 
    component: ClienteVehiculosComponent, 
    canActivate: [authGuard, roleGuard(['cliente'])] 
  },
  { 
    path: 'cliente/mantenimientos/vehiculo/:idVehiculo', 
    component: MantenimientosListComponent, 
    canActivate: [authGuard, roleGuard(['cliente'])] 
  },
  
  // Redirecciones para compatibilidad
  { path: 'vehiculos', redirectTo: '/empleado/vehiculos', pathMatch: 'full' },
  { path: 'mantenimientos', redirectTo: '/empleado/mantenimientos', pathMatch: 'full' },
  { path: '**', redirectTo: '' }
];
