import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VehiculoService, Vehiculo } from '../../../services/vehiculo.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioService, Usuario } from '../../../services/usuario.service';

@Component({
  selector: 'app-vehiculo-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehiculo-form.component.html',
  styleUrl: './vehiculo-form.component.css'
})
export class VehiculoFormComponent implements OnInit {
  vehiculoForm: FormGroup;
  isEdit = false;
  vehiculoId?: number;
  errorMessage = '';
  usuarios: Usuario[] = [];
  loadingUsuarios = false;

  constructor(
    private fb: FormBuilder,
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    this.vehiculoForm = this.fb.group({
      placa: ['', [Validators.required, Validators.maxLength(20)]],
      marca: ['', [Validators.required, Validators.maxLength(50)]],
      modelo: ['', [Validators.required, Validators.maxLength(50)]],
      anio: [null],
      idUsuario: [null]
    });
  }

  ngOnInit(): void {
    this.loadUsuarios();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.vehiculoId = +id;
      this.loadVehiculo(this.vehiculoId);
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

  loadVehiculo(id: number): void {
    this.vehiculoService.getById(id).subscribe({
      next: (vehiculo) => {
        this.vehiculoForm.patchValue(vehiculo);
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar vehículo';
        console.error(error);
      }
    });
  }

  onSubmit(): void {
    if (this.vehiculoForm.valid) {
      const vehiculo: Vehiculo = this.vehiculoForm.value;
      
      if (this.isEdit && this.vehiculoId) {
        this.vehiculoService.update(this.vehiculoId, vehiculo).subscribe({
          next: () => {
            const user = this.authService.getUserData();
            const role = user?.rol || 'empleado';
            const baseRoute = role === 'admin' ? '/admin/vehiculos' : '/empleado/vehiculos';
            this.router.navigate([baseRoute]);
          },
          error: (error) => {
            this.errorMessage = error.error?.error || 'Error al actualizar vehículo';
          }
        });
      } else {
        this.vehiculoService.create(vehiculo).subscribe({
          next: () => {
            const user = this.authService.getUserData();
            const role = user?.rol || 'empleado';
            const baseRoute = role === 'admin' ? '/admin/vehiculos' : '/empleado/vehiculos';
            this.router.navigate([baseRoute]);
          },
          error: (error) => {
            this.errorMessage = error.error?.error || 'Error al crear vehículo';
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
    const baseRoute = role === 'admin' ? '/admin/vehiculos' : '/empleado/vehiculos';
    this.router.navigate([baseRoute]);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.vehiculoForm.controls).forEach(key => {
      const control = this.vehiculoForm.get(key);
      control?.markAsTouched();
    });
  }

  get placa() {
    return this.vehiculoForm.get('placa');
  }

  get marca() {
    return this.vehiculoForm.get('marca');
  }

  get modelo() {
    return this.vehiculoForm.get('modelo');
  }
}

