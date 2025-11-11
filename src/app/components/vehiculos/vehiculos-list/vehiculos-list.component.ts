import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { VehiculoService, Vehiculo } from '../../../services/vehiculo.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-vehiculos-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './vehiculos-list.component.html',
  styleUrl: './vehiculos-list.component.css'
})
export class VehiculosListComponent implements OnInit {
  vehiculos: Vehiculo[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadVehiculos();
  }

  loadVehiculos(): void {
    this.loading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();
    console.log('Cargando vehículos...');
    this.vehiculoService.getAll().subscribe({
      next: (data) => {
        console.log('Vehículos recibidos:', data);
        this.vehiculos = data || [];
        this.loading = false;
        this.cdr.detectChanges();
        console.log('Loading después de recibir datos:', this.loading);
        console.log('Vehículos después de recibir datos:', this.vehiculos.length);
        if (this.vehiculos.length === 0) {
          console.log('No hay vehículos en la respuesta');
        }
      },
      error: (error) => {
        console.error('Error completo al cargar vehículos:', error);
        console.error('Status:', error.status);
        console.error('StatusText:', error.statusText);
        console.error('Error body:', error.error);
        this.errorMessage = error.error?.error || error.message || `Error al cargar vehículos (${error.status || 'sin conexión'}). Verifica tu conexión y que el backend esté corriendo.`;
        this.loading = false;
        this.vehiculos = [];
        this.cdr.detectChanges();
      }
    });
  }


  deleteVehiculo(id: number): void {
    if (confirm('¿Está seguro de eliminar este vehículo?')) {
      this.vehiculoService.delete(id).subscribe({
        next: () => {
          this.loadVehiculos();
        },
        error: (error) => {
          this.errorMessage = 'Error al eliminar vehículo';
          console.error(error);
        }
      });
    }
  }


  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getUserName(): string {
    const user = this.authService.getUserData();
    return user?.nombre || 'Usuario';
  }

  getUserRole(): string {
    const user = this.authService.getUserData();
    return user?.rol || 'empleado';
  }

  getVehiculosRoute(): string {
    return this.getUserRole() === 'admin' ? '/admin/vehiculos' : '/empleado/vehiculos';
  }

  getMantenimientosRoute(): string {
    return this.getUserRole() === 'admin' ? '/admin/mantenimientos' : '/empleado/mantenimientos';
  }

  editVehiculo(id: number): void {
    this.router.navigate([this.getVehiculosRoute() + '/editar', id]);
  }

  viewMantenimientos(id: number): void {
    this.router.navigate([this.getMantenimientosRoute() + '/vehiculo', id]);
  }

  get isLoading(): boolean {
    return this.loading;
  }

  get hasVehiculos(): boolean {
    return this.vehiculos.length > 0;
  }
}

