import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './Login.html',
  styleUrls: ['./Login.css']
})
export class LoginComponent {
  // Propiedades del formulario
  username: string = '';
  password: string = '';
  rememberMe: boolean = false;
  loading: boolean = false;
  errorMessage: string = '';
  
  // Estados de la interfaz
  showRegisterForm: boolean = false;
  showForgotPassword: boolean = false;
  
  // Datos del formulario de registro
  registerData = {
    fullName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: ''
  };

  constructor(
    private apiService: ApiService,
    private router: Router
  ) {}

  // Método para iniciar sesión
  onLogin() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor, complete todos los campos';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    
    // Usar el username como email (el campo acepta email)
    this.apiService.login(this.username, this.password).subscribe({
      next: (response) => {
        if (response.success) {
          // Guardar información del usuario en localStorage
          localStorage.setItem('userId', response.id.toString());
          localStorage.setItem('userEmail', response.email);
          localStorage.setItem('userName', response.name);
          
          if (this.rememberMe) {
            localStorage.setItem('rememberMe', 'true');
          }
          
          // Redirigir al dashboard
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = 'Credenciales inválidas';
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error en login:', error);
        
        // Detectar si no hay conexión con el servidor
        if (error.status === 0 || error.message?.includes('Failed to fetch') || error.message?.includes('NetworkError')) {
          this.errorMessage = 'No se puede conectar con el servidor. Verifique que el backend esté corriendo en http://localhost:8080';
        } else if (error.status === 401) {
          this.errorMessage = error.error?.message || 'Credenciales inválidas. Verifique su email y contraseña.';
        } else if (error.status === 404) {
          this.errorMessage = 'Endpoint no encontrado. Verifique la URL del servidor.';
        } else {
          this.errorMessage = error.error?.message || `Error al iniciar sesión (${error.status}). Por favor, intente nuevamente.`;
        }
        
        this.loading = false;
      }
    });
  }

  // Método para mostrar formulario de registro
  showRegister() {
    this.showRegisterForm = true;
    this.showForgotPassword = false;
  }

  // Método para mostrar formulario de recuperar contraseña
  showForgot() {
    this.showForgotPassword = true;
    this.showRegisterForm = false;
  }

  // Método para volver al login
  backToLogin() {
    this.showRegisterForm = false;
    this.showForgotPassword = false;
  }

  // Método para registrar nuevo usuario
  onRegister() {
    if (!this.validateRegisterForm()) {
      return;
    }
    
    this.loading = true;
    this.errorMessage = '';
    
    // Dividir el nombre completo
    const nameParts = this.registerData.fullName.trim().split(' ');
    const firstName = nameParts[0] || this.registerData.fullName;
    const lastName = nameParts.slice(1).join(' ') || firstName; // Si no hay apellido, usar el nombre
    
    // Preparar datos para el registro
    const userData = {
      name: firstName,
      lastName: lastName,
      email: this.registerData.email.trim(),
      password: this.registerData.password,
      phone: this.registerData.phone.trim() || '',
      address: ''
    };
    
    console.log('Datos de registro a enviar:', userData);
    
    this.apiService.register(userData).subscribe({
      next: (response) => {
        console.log('Respuesta del servidor:', response);
        if (response.success) {
          alert('¡Registro exitoso! Ya puedes iniciar sesión.');
          this.backToLogin();
          // Limpiar el formulario
          this.registerData = {
            fullName: '',
            email: '',
            phone: '',
            password: '',
            confirmPassword: ''
          };
        } else {
          this.errorMessage = response.message || 'Error al registrar usuario';
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error completo en registro:', error);
        console.error('Error status:', error.status);
        console.error('Error message:', error.message);
        console.error('Error error:', error.error);
        
        // Manejo más detallado de errores
        if (error.status === 0) {
          this.errorMessage = 'No se puede conectar con el servidor. Verifique que el backend esté corriendo.';
        } else if (error.status === 409) {
          this.errorMessage = error.error?.message || 'El email ya está registrado';
        } else if (error.status === 400) {
          this.errorMessage = error.error?.message || 'Datos inválidos. Por favor, verifique la información ingresada.';
        } else {
          this.errorMessage = error.error?.message || `Error al registrar usuario (${error.status}). Por favor, intente nuevamente.`;
        }
        this.loading = false;
      }
    });
  }

  // Validar formulario de registro
  validateRegisterForm(): boolean {
    if (!this.registerData.fullName || !this.registerData.email || 
        !this.registerData.phone || !this.registerData.password || 
        !this.registerData.confirmPassword) {
      alert('Por favor, complete todos los campos');
      return false;
    }
    
    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return false;
    }
    
    return true;
  }

  // Método para recuperar contraseña
  onForgotPassword() {
    const email = (document.getElementById('forgotEmail') as HTMLInputElement)?.value;
    if (email) {
      console.log('Enviando email de recuperación a:', email);
      alert('Se ha enviado un enlace de recuperación a su email');
      this.backToLogin();
    } else {
      alert('Por favor, ingrese su email');
    }
  }
}






