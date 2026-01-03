import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-home.html',
  styleUrls: ['./user-home.css']
})
export class UserHomeComponent {
  // Pas de logique spéciale pour la page d'accueil statique
}
