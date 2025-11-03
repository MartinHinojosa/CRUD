import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  
  constructor(private http: HttpClient) { }
  
  // Login
  login(email: string, password: string): Observable<any> {
    return this.http.post(`${API_URL}/users/login`, { email, password });
  }
  
  // Obtener perfil de usuario
  getUserProfile(userId: number): Observable<any> {
    return this.http.get(`${API_URL}/users/${userId}`);
  }
  
  // Obtener dashboard completo del usuario
  getUserDashboard(userId: number): Observable<any> {
    return this.http.get(`${API_URL}/dashboard/user/${userId}`);
  }
  
  // Verificar conexión con el servidor
  checkHealth(): Observable<any> {
    return this.http.get(`${API_URL}/health`);
  }
  
  // Registro de nuevos usuarios (solo clientes)
  register(userData: any): Observable<any> {
    return this.http.post(`${API_URL}/users/register`, userData);
  }
  
  // Obtener reparaciones de un cliente
  getRepairsByClient(clientId: number): Observable<any> {
    return this.http.get(`${API_URL}/repairs/client/${clientId}`);
  }
  
  // Obtener todas las reparaciones (para admin/recepcionista)
  getAllRepairs(): Observable<any> {
    return this.http.get(`${API_URL}/repairs/all`);
  }
  
  // Crear nueva reparación/cita
  createRepair(repairData: any): Observable<any> {
    return this.http.post(`${API_URL}/repairs/create`, repairData);
  }
  
  // Actualizar estado de reparación
  updateRepairStatus(repairId: number, status: string): Observable<any> {
    return this.http.put(`${API_URL}/repairs/${repairId}/status`, { status });
  }
  
  // Obtener todos los clientes (para admin/recepcionista)
  getAllClients(): Observable<any> {
    return this.http.get(`${API_URL}/clients/all`);
  }
  
  // Obtener todos los carros (para admin/recepcionista)
  getAllCars(): Observable<any> {
    return this.http.get(`${API_URL}/cars/all`);
  }
  
  // Crear un nuevo carro
  createCar(carData: any): Observable<any> {
    return this.http.post(`${API_URL}/cars/create`, carData);
  }
  
  // Obtener carros de un cliente específico
  getCarsByClient(clientId: number): Observable<any> {
    return this.http.get(`${API_URL}/cars/client/${clientId}`);
  }
}

