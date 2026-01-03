import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../_services/auth';
import { AccountService } from '../../../_services/account.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.html',
  styleUrls: ['./user-profile.css']
})
export class UserProfileComponent implements OnInit {
  user: any = { fullname: 'Chargement...' };

  // CORRECTION 1 : Ajout de la propriété 'iban' ici pour éviter l'erreur TS2339
  account = {
    id: '',
    balance: 0,
    type: '',
    iban: ''
  };

  transactions: any[] = [];
  errorMessage: string = '';

  constructor(
      private authService: AuthService,
      private accountService: AccountService
  ) {}

  ngOnInit() {
    const currentUser = this.authService.getUser();

    if (currentUser) {
      this.user = currentUser;
      this.user.fullname = currentUser.name || currentUser.email;
      this.loadUserAccount(currentUser.id);
    }
  }

  loadUserAccount(userId: number) {
    this.accountService.getAccountsByCustomer(userId).subscribe({
      next: (accounts) => {
        if (accounts && accounts.length > 0) {
          const mainAccount = accounts[0];

          this.account = {
            id: mainAccount.id,
            balance: mainAccount.balance,
            type: mainAccount.type,
            // CORRECTION 2 : On utilise l'ID comme IBAN (car le backend n'a pas de champ IBAN)
            iban: mainAccount.id
          };

          if (mainAccount.accountOperations) {
            this.transactions = mainAccount.accountOperations;
          }
        } else {
          this.errorMessage = "Aucun compte bancaire associé.";
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = "Impossible de charger les informations bancaires.";
      }
    });
  }
}