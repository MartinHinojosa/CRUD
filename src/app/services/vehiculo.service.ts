import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Vehiculo {
  idVehiculo?: number;
  placa: string;
  marca: string;
  modelo: string;
  anio?: number;
  idUsuario?: number;
  nombrePropietario?: string;
}

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {
  private apiUrl = 'http://localhost:8080/api/vehiculos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(this.apiUrl);
  }

  getById(id: number): Observable<Vehiculo> {
    return this.http.get<Vehiculo>(`${this.apiUrl}/${id}`);
  }

  getByUsuario(idUsuario: number): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.apiUrl}/usuario/${idUsuario}`);
  }

  create(vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(this.apiUrl, vehiculo);
  }

  update(id: number, vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.apiUrl}/${id}`, vehiculo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

