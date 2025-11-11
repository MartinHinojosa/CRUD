import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Mantenimiento {
  idMantenimiento?: number;
  idVehiculo: number;
  placaVehiculo?: string;
  tipoServicio: string;
  fechaServicio: string;
  costo?: number;
  notas?: string;
  idUsuario?: number;
  nombreUsuario?: string;
}

@Injectable({
  providedIn: 'root'
})
export class MantenimientoService {
  private apiUrl = 'http://localhost:8080/api/mantenimientos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Mantenimiento[]> {
    return this.http.get<Mantenimiento[]>(this.apiUrl);
  }

  getById(id: number): Observable<Mantenimiento> {
    return this.http.get<Mantenimiento>(`${this.apiUrl}/${id}`);
  }

  getByVehiculo(idVehiculo: number): Observable<Mantenimiento[]> {
    return this.http.get<Mantenimiento[]>(`${this.apiUrl}/vehiculo/${idVehiculo}`);
  }

  getByUsuario(idUsuario: number): Observable<Mantenimiento[]> {
    return this.http.get<Mantenimiento[]>(`${this.apiUrl}/usuario/${idUsuario}`);
  }

  create(mantenimiento: Mantenimiento): Observable<Mantenimiento> {
    return this.http.post<Mantenimiento>(this.apiUrl, mantenimiento);
  }

  update(id: number, mantenimiento: Mantenimiento): Observable<Mantenimiento> {
    return this.http.put<Mantenimiento>(`${this.apiUrl}/${id}`, mantenimiento);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

