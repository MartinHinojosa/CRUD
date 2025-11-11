import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  // Agregar token si existe
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  } else {
    // Solo loguear si no es una petición de autenticación
    if (!req.url.includes('/api/auth/')) {
      console.warn('No hay token disponible para la petición:', req.url);
    }
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('Error HTTP:', error.status, error.url, error.message);
      
      // Si el error es 401 (no autorizado), redirigir al login
      if (error.status === 401) {
        console.warn('Token inválido o expirado, redirigiendo al login');
        authService.logout();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};

