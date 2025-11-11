import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: '',
    renderMode: RenderMode.Prerender
  },
  {
    path: 'login',
    renderMode: RenderMode.Prerender
  },
  {
    path: 'register/:role',
    renderMode: RenderMode.Prerender
  },
  // Rutas de Administrador
  {
    path: 'admin/vehiculos',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/vehiculos/nuevo',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/vehiculos/editar/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/mantenimientos',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/mantenimientos/nuevo',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/mantenimientos/editar/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'admin/mantenimientos/vehiculo/:idVehiculo',
    renderMode: RenderMode.Server
  },
  // Rutas de Empleado
  {
    path: 'empleado/vehiculos',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/vehiculos/nuevo',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/vehiculos/editar/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/mantenimientos',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/mantenimientos/nuevo',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/mantenimientos/editar/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'empleado/mantenimientos/vehiculo/:idVehiculo',
    renderMode: RenderMode.Server
  },
  // Redirecciones
  {
    path: 'vehiculos',
    renderMode: RenderMode.Server
  },
  {
    path: 'mantenimientos',
    renderMode: RenderMode.Server
  },
  {
    path: '**',
    renderMode: RenderMode.Server
  }
];

