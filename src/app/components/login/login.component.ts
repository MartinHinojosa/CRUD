import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.errorMessage = '';
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          // Redirigir según el rol del usuario
          if (response.rol === 'admin') {
            this.router.navigate(['/admin/vehiculos']);
          } else if (response.rol === 'cliente') {
            this.router.navigate(['/cliente/vehiculos']);
          } else {
            this.router.navigate(['/empleado/vehiculos']);
          }
        },
        error: (error) => {
          this.errorMessage = error.error?.error || 'Error al iniciar sesión';
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      control?.markAsTouched();
    });
  }

  get correo() {
    return this.loginForm.get('correo');
  }

  get password() {
    return this.loginForm.get('password');
  }
}

