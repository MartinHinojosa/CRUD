import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { VehiculoService, Vehiculo } from '../../../services/vehiculo.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-cliente-vehiculos',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cliente-vehiculos.component.html',
  styleUrl: './cliente-vehiculos.component.css'
})
export class ClienteVehiculosComponent implements OnInit {
  vehiculos: Vehiculo[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private usuarioService: UsuarioService,
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
    
    const user = this.authService.getUserData();
    if (!user || !user.correo) {
      this.router.navigate(['/login']);
      return;
    }

    // Obtener el idUsuario del usuario actual
    this.usuarioService.getAll().subscribe({
      next: (usuarios) => {
        const usuarioActual = usuarios.find(u => u.correo === user.correo);
        if (usuarioActual && usuarioActual.idUsuario) {
          // Obtener vehículos del usuario actual
          this.vehiculoService.getByUsuario(usuarioActual.idUsuario).subscribe({
            next: (data) => {
              this.vehiculos = data || [];
              this.loading = false;
              this.cdr.detectChanges();
            },
            error: (error) => {
              console.error('Error al cargar vehículos:', error);
              this.errorMessage = 'Error al cargar vehículos';
              this.loading = false;
              this.cdr.detectChanges();
            }
          });
        } else {
          this.errorMessage = 'Usuario no encontrado';
          this.loading = false;
          this.cdr.detectChanges();
        }
      },
      error: (error) => {
        console.error('Error al cargar usuarios:', error);
        this.errorMessage = 'Error al cargar información del usuario';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getUserName(): string {
    const user = this.authService.getUserData();
    return user?.nombre || 'Cliente';
  }

  viewMantenimientos(id: number): void {
    this.router.navigate(['/cliente/mantenimientos/vehiculo', id]);
  }

  get isLoading(): boolean {
    return this.loading;
  }

  get hasVehiculos(): boolean {
    return this.vehiculos.length > 0;
  }
}

