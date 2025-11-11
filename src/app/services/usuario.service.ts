import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

export interface Usuario {
  idUsuario: number;
  nombre: string;
  correo: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private http = inject(HttpClient);
  private platformId = inject(PLATFORM_ID);

  getAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`);
  }
}

