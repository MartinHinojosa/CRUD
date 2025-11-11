import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MantenimientoService, Mantenimiento } from '../../../services/mantenimiento.service';
import { VehiculoService, Vehiculo } from '../../../services/vehiculo.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioService, Usuario } from '../../../services/usuario.service';

@Component({
  selector: 'app-mantenimiento-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './mantenimiento-form.component.html',
  styleUrl: './mantenimiento-form.component.css'
})
export class MantenimientoFormComponent implements OnInit {
  mantenimientoForm: FormGroup;
  isEdit = false;
  mantenimientoId?: number;
  errorMessage = '';
  vehiculos: Vehiculo[] = [];
  usuarios: Usuario[] = [];
  loadingUsuarios = false;

  constructor(
    private fb: FormBuilder,
    private mantenimientoService: MantenimientoService,
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    this.mantenimientoForm = this.fb.group({
      idVehiculo: [null, Validators.required],
      tipoServicio: ['', [Validators.required, Validators.maxLength(100)]],
      fechaServicio: ['', Validators.required],
      costo: [null],
      notas: [''],
      idUsuario: [null]
    });
  }

  ngOnInit(): void {
    this.loadVehiculos();
    this.loadUsuarios();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.mantenimientoId = +id;
      this.loadMantenimiento(this.mantenimientoId);
    }
  }

  loadUsuarios(): void {
    this.loadingUsuarios = true;
    this.usuarioService.getAll().subscribe({
      next: (data) => {
        this.usuarios = data || [];
        this.loadingUsuarios = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error al cargar usuarios:', error);
        this.loadingUsuarios = false;
        this.cdr.detectChanges();
      }
    });
  }

  loadVehiculos(): void {
    this.vehiculoService.getAll().subscribe({
      next: (data) => {
        this.vehiculos = data;
      },
      error: (error) => {
        console.error('Error al cargar vehículos', error);
      }
    });
  }

  loadMantenimiento(id: number): void {
    this.mantenimientoService.getById(id).subscribe({
      next: (mantenimiento) => {
        // Convertir fecha de string a formato para input date
        const fecha = mantenimiento.fechaServicio.split('T')[0];
        this.mantenimientoForm.patchValue({
          ...mantenimiento,
          fechaServicio: fecha
        });
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar mantenimiento';
        console.error(error);
      }
    });
  }

  onSubmit(): void {
    if (this.mantenimientoForm.valid) {
      const mantenimiento: Mantenimiento = this.mantenimientoForm.value;
      
      if (this.isEdit && this.mantenimientoId) {
        this.mantenimientoService.update(this.mantenimientoId, mantenimiento).subscribe({
          next: () => {
            const user = this.authService.getUserData();
            const role = user?.rol || 'empleado';
            const baseRoute = role === 'admin' ? '/admin/mantenimientos' : '/empleado/mantenimientos';
            this.router.navigate([baseRoute]);
          },
          error: (error) => {
            this.errorMessage = error.error?.error || 'Error al actualizar mantenimiento';
          }
        });
      } else {
        this.mantenimientoService.create(mantenimiento).subscribe({
          next: () => {
            const user = this.authService.getUserData();
            const role = user?.rol || 'empleado';
            const baseRoute = role === 'admin' ? '/admin/mantenimientos' : '/empleado/mantenimientos';
            this.router.navigate([baseRoute]);
          },
          error: (error) => {
            this.errorMessage = error.error?.error || 'Error al crear mantenimiento';
          }
        });
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  cancel(): void {
    const user = this.authService.getUserData();
    const role = user?.rol || 'empleado';
    const baseRoute = role === 'admin' ? '/admin/mantenimientos' : '/empleado/mantenimientos';
    this.router.navigate([baseRoute]);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.mantenimientoForm.controls).forEach(key => {
      const control = this.mantenimientoForm.get(key);
      control?.markAsTouched();
    });
  }

  get idVehiculo() {
    return this.mantenimientoForm.get('idVehiculo');
  }

  get tipoServicio() {
    return this.mantenimientoForm.get('tipoServicio');
  }

  get fechaServicio() {
    return this.mantenimientoForm.get('fechaServicio');
  }
}

