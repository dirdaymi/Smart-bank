import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-root',
    template: `
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          
          <div class="text-center mb-4">
            <h1 class="text-primary"><i class="fas fa-university"></i> Smart Bank</h1>
            <p class="lead">Accès Sécurisé</p>
          </div>

          <div class="card shadow-lg" *ngIf="!estConnecte">
            <div class="card-body p-4">
              <h4 class="card-title text-center mb-4">Authentification</h4>
              
              <div class="alert alert-danger" *ngIf="messageErreur">
                <i class="fas fa-exclamation-circle"></i> {{ messageErreur }}
              </div>

              <form (ngSubmit)="seConnecter()">
                <div class="mb-3">
                  <label class="form-label">Nom d'utilisateur</label>
                  <input type="text" class="form-control" [(ngModel)]="username" name="username" placeholder="ex: admin" required>
                </div>
                
                <div class="mb-3">
                  <label class="form-label">Mot de passe</label>
                  <input type="password" class="form-control" [(ngModel)]="password" name="password" placeholder="ex: admin123" required>
                </div>

                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary btn-lg" [disabled]="enChargement">
                    <span *ngIf="enChargement" class="spinner-border spinner-border-sm me-2"></span>
                    {{ enChargement ? 'Connexion...' : 'Se Connecter' }}
                  </button>
                </div>
              </form>
              
              <div class="mt-3 text-center text-muted">
                <small>Comptes de test : admin / admin123</small>
              </div>
            </div>
          </div>

          <div class="card border-success shadow-lg" *ngIf="estConnecte">
            <div class="card-header bg-success text-white">
              <h3 class="mb-0"><i class="fas fa-check-circle"></i> Connexion Réussie !</h3>
            </div>
            <div class="card-body text-center p-5">
              <h4>Bienvenue, <span class="text-success fw-bold">{{ userConnecte }}</span></h4>
              <p class="lead">Vous êtes connecté au Backend Spring Boot.</p>
              <hr>
              <div class="d-flex justify-content-around mt-4">
                <button class="btn btn-outline-primary"><i class="fas fa-wallet"></i> Mes Comptes</button>
                <button class="btn btn-outline-dark"><i class="fas fa-exchange-alt"></i> Virements</button>
              </div>
              <button class="btn btn-danger mt-5" (click)="seDeconnecter()">Se Déconnecter</button>
            </div>
          </div>

        </div>
      </div>
    </div>
  `,
    styles: [`
    .card { border-radius: 15px; border: none; }
    .form-control { padding: 12px; border-radius: 8px; }
    .btn-lg { padding: 12px; border-radius: 8px; }
  `]
})
export class AppComponent {
    // Variables du formulaire
    username = '';
    password = '';

    // État de l'application
    estConnecte = false;
    enChargement = false;
    messageErreur = '';
    userConnecte = '';

    // Injection du HttpClient pour parler au Backend
    constructor(private http: HttpClient) {}

    seConnecter() {
        this.enChargement = true;
        this.messageErreur = '';

        // L'objet à envoyer au Backend (correspond à LoginRequest.java)
        const loginData = {
            username: this.username,
            password: this.password
        };

        // Appel API vers Spring Boot
        // URL basée sur votre config (port 8080, context /api)
        this.http.post<any>('http://localhost:8080/api/auth/login', loginData)
            .subscribe({
                next: (response) => {
                    // SUCCÈS
                    console.log('Réponse Backend:', response);
                    this.enChargement = false;
                    this.estConnecte = true;
                    this.userConnecte = response.username || this.username;
                    // On stocke le token (optionnel pour ce test rapide)
                    localStorage.setItem('token', response.token);
                },
                error: (err) => {
                    // ERREUR
                    console.error('Erreur Backend:', err);
                    this.enChargement = false;
                    if (err.status === 401 || err.status === 403) {
                        this.messageErreur = "Identifiants incorrects. Vérifiez votre login/mot de passe.";
                    } else if (err.status === 0) {
                        this.messageErreur = "Impossible de contacter le serveur (Port 8080). Vérifiez que le Backend tourne.";
                    } else {
                        this.messageErreur = "Erreur technique : " + (err.error?.message || err.message);
                    }
                }
            });
    }

    seDeconnecter() {
        this.estConnecte = false;
        this.username = '';
        this.password = '';
        localStorage.removeItem('token');
    }
}