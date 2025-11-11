import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { MantenimientoService, Mantenimiento } from '../../../services/mantenimiento.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-mantenimientos-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mantenimientos-list.component.html',
  styleUrl: './mantenimientos-list.component.css'
})
export class MantenimientosListComponent implements OnInit {
  mantenimientos: Mantenimiento[] = [];
  loading = false;
  errorMessage = '';
  idVehiculo?: number;

  constructor(
    private mantenimientoService: MantenimientoService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idVehiculo = params['idVehiculo'] ? +params['idVehiculo'] : undefined;
      this.loadMantenimientos();
    });
  }

  loadMantenimientos(): void {
    this.loading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();
    console.log('Cargando mantenimientos...', this.idVehiculo ? `para vehículo ${this.idVehiculo}` : 'todos');
    const observable = this.idVehiculo 
      ? this.mantenimientoService.getByVehiculo(this.idVehiculo)
      : this.mantenimientoService.getAll();
    
    observable.subscribe({
      next: (data) => {
        console.log('Mantenimientos recibidos:', data);
        this.mantenimientos = data || [];
        this.loading = false;
        this.cdr.detectChanges();
        console.log('Loading después de recibir datos:', this.loading);
        console.log('Mantenimientos después de recibir datos:', this.mantenimientos.length);
        if (this.mantenimientos.length === 0) {
          console.log('No hay mantenimientos en la respuesta');
        }
      },
      error: (error) => {
        console.error('Error completo al cargar mantenimientos:', error);
        console.error('Status:', error.status);
        console.error('StatusText:', error.statusText);
        console.error('Error body:', error.error);
        this.errorMessage = error.error?.error || error.message || `Error al cargar mantenimientos (${error.status || 'sin conexión'}). Verifica tu conexión y que el backend esté corriendo.`;
        this.loading = false;
        this.mantenimientos = [];
        this.cdr.detectChanges();
      }
    });
  }


  deleteMantenimiento(id: number): void {
    if (confirm('¿Está seguro de eliminar este mantenimiento?')) {
      this.mantenimientoService.delete(id).subscribe({
        next: () => {
          this.loadMantenimientos();
        },
        error: (error) => {
          this.errorMessage = 'Error al eliminar mantenimiento';
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

  isCliente(): boolean {
    return this.getUserRole() === 'cliente';
  }

  getVehiculosRoute(): string {
    return this.getUserRole() === 'admin' ? '/admin/vehiculos' : '/empleado/vehiculos';
  }

  getMantenimientosRoute(): string {
    return this.getUserRole() === 'admin' ? '/admin/mantenimientos' : '/empleado/mantenimientos';
  }

  editMantenimiento(id: number): void {
    this.router.navigate([this.getMantenimientosRoute() + '/editar', id]);
  }

  get isLoading(): boolean {
    return this.loading;
  }

  get hasMantenimientos(): boolean {
    return this.mantenimientos.length > 0;
  }
}

