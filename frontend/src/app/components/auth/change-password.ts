import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../_services/auth';

@Component({
    selector: 'app-change-password',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './change-password.html'
})
export class ChangePasswordComponent {
    // CORRECTION ICI : Utilisation de 'old' et 'new' pour correspondre à votre HTML
    passwords = {
        old: '',
        new: ''
    };

    errorMessage: string = '';
    successMessage: string = '';
    isLoading: boolean = false;

    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    updatePassword() {
        // Vérification avec les bons noms de propriétés
        if (!this.passwords.old || !this.passwords.new) {
            this.errorMessage = "Veuillez remplir tous les champs.";
            return;
        }

        this.isLoading = true;
        this.errorMessage = '';
        this.successMessage = '';

        // Appel au service (qui attend oldPassword/newPassword, on lui passe donc nos valeurs)
        this.authService.changePassword(this.passwords.old, this.passwords.new)
            .subscribe({
                next: (res: any) => {
                    this.isLoading = false;
                    this.successMessage = "Mot de passe modifié !";
                    this.passwords = { old: '', new: '' };

                    setTimeout(() => {
                        this.router.navigate(['/user/profile']);
                    }, 2000);
                },
                error: (err: any) => {
                    this.isLoading = false;
                    console.error(err);
                    this.errorMessage = "Erreur : Vérifiez votre ancien mot de passe.";
                }
            });
    }
}