import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
  correo: string;
  password: string;
}

export interface RegisterRequest {
  nombre: string;
  correo: string;
  password: string;
  rol?: string;
}

export interface LoginResponse {
  token: string;
  correo: string;
  nombre: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'auth_token';
  private userKey = 'user_data';
  private platformId = inject(PLATFORM_ID);

  constructor(private http: HttpClient) {}

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (this.isBrowser()) {
          this.setToken(response.token);
          this.setUserData(response);
        }
      })
    );
  }

  register(data: RegisterRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, data).pipe(
      tap(response => {
        if (this.isBrowser()) {
          this.setToken(response.token);
          this.setUserData(response);
        }
      })
    );
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.userKey);
    }
  }

  getToken(): string | null {
    if (!this.isBrowser()) {
      return null;
    }
    return localStorage.getItem(this.tokenKey);
  }

  getUserData(): LoginResponse | null {
    if (!this.isBrowser()) {
      return null;
    }
    const data = localStorage.getItem(this.userKey);
    return data ? JSON.parse(data) : null;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private setToken(token: string): void {
    if (this.isBrowser()) {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  private setUserData(user: LoginResponse): void {
    if (this.isBrowser()) {
      localStorage.setItem(this.userKey, JSON.stringify(user));
    }
  }
}

