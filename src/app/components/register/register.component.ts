import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';
  selectedRole: string = 'empleado';
  roleName: string = 'Empleado';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.selectedRole = params['role'] || 'empleado';
      this.roleName = this.selectedRole === 'admin' ? 'Administrador' : 'Empleado';
      // El rol se establece automáticamente, no se muestra en el formulario
    });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    return null;
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.errorMessage = '';
      const { confirmPassword, ...registerData } = this.registerForm.value;
      registerData.rol = this.selectedRole;
      
      this.authService.register(registerData).subscribe({
        next: () => {
          // Redirigir según el rol
          if (this.selectedRole === 'admin') {
            this.router.navigate(['/admin/vehiculos']);
          } else {
            this.router.navigate(['/empleado/vehiculos']);
          }
        },
        error: (error) => {
          this.errorMessage = error.error?.error || 'Error al registrar usuario';
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.registerForm.controls).forEach(key => {
      const control = this.registerForm.get(key);
      control?.markAsTouched();
    });
  }

  get nombre() {
    return this.registerForm.get('nombre');
  }

  get correo() {
    return this.registerForm.get('correo');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get confirmPassword() {
    return this.registerForm.get('confirmPassword');
  }
}

