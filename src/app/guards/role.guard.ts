import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isAuthenticated()) {
      router.navigate(['/login']);
      return false;
    }

    const user = authService.getUserData();
    if (!user || !allowedRoles.includes(user.rol)) {
      // Redirigir según el rol del usuario
      if (user?.rol === 'admin') {
        router.navigate(['/admin/vehiculos']);
      } else if (user?.rol === 'cliente') {
        router.navigate(['/cliente/vehiculos']);
      } else {
        router.navigate(['/empleado/vehiculos']);
      }
      return false;
    }

    return true;
  };
};

