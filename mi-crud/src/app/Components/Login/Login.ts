import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

  // Método para iniciar sesión
  onLogin() {
    if (this.username && this.password) {
      console.log('Iniciando sesión:', {
        username: this.username,
        password: this.password,
        rememberMe: this.rememberMe
      });
      // Aquí iría la lógica de autenticación
    } else {
      alert('Por favor, complete todos los campos');
    }
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
    if (this.validateRegisterForm()) {
      console.log('Registrando usuario:', this.registerData);
      // Aquí iría la lógica de registro
      this.backToLogin();
    }
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






