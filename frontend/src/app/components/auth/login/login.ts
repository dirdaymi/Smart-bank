import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../_services/auth'; // <--- Import added

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  email = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    // Explicitly typing 'data' and 'err' as 'any' to satisfy TypeScript strict mode
    this.authService.login(this.email, this.password).subscribe({
      next: (data: any) => {
        console.log('Login successful', data);
        this.router.navigate(['/user/profile']);
      },
      error: (err: any) => {
        alert("Échec de connexion");
        console.error(err);
      }
    });
  }
}
