import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login'; // You need this endpoint in Java

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { email, password }).pipe(
      tap(response => {
        // Save token to LocalStorage
        if (response.accessToken) {
          localStorage.setItem('token', response.accessToken);
          localStorage.setItem('user', JSON.stringify(response.user));
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }


  hasRole(role: string): boolean {
    const user = this.getUser();
    // Suppose que votre User a un champ 'roles' (tableau de strings) ou 'role' (string)
    if (user && user.roles) {
      return user.roles.includes(role);
    }
    return false; // Par défaut, non
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user') || '{}');
  }

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    // On suppose que votre API Java écoute sur /api/auth/change-password
    // et attend un objet { oldPassword, newPassword } ou similaire.
    const apiUrl = 'http://localhost:8080/api/auth/change-password';

    return this.http.post<any>(apiUrl, {
      oldPassword: oldPassword,
      newPassword: newPassword
    });
  }
}
