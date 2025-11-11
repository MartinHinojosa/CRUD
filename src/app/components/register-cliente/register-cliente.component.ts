import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register-cliente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register-cliente.component.html',
  styleUrl: './register-cliente.component.css'
})
export class RegisterClienteComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    // Verificar que el usuario sea administrador
    const user = this.authService.getUserData();
    if (!user || user.rol !== 'admin') {
      this.router.navigate(['/admin/vehiculos']);
    }
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
      this.successMessage = '';
      
      const formValue = this.registerForm.value;
      const registerData = {
        nombre: formValue.nombre,
        correo: formValue.correo,
        password: formValue.password,
        rol: 'cliente' // Siempre se registra como cliente
      };

      this.authService.register(registerData).subscribe({
        next: () => {
          this.successMessage = 'Cliente registrado exitosamente';
          this.registerForm.reset();
          setTimeout(() => {
            this.router.navigate(['/admin/vehiculos']);
          }, 2000);
        },
        error: (error) => {
          this.errorMessage = error.error?.error || error.message || 'Error al registrar cliente';
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

