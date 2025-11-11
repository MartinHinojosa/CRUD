import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent {
  constructor(private router: Router) {}

  selectRole(role: 'admin' | 'empleado'): void {
    this.router.navigate(['/register', role]);
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}

