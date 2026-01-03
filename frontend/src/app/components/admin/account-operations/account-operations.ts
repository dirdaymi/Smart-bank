import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../../_services/account.service';
import { BankAccount, AccountOperation } from '../../../_models/core-models';

@Component({
  selector: 'app-account-operations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './account-operations.html'
})
export class AccountOperationsComponent {
  accountId: string = '';
  account?: BankAccount;
  operations: AccountOperation[] = [];
  errorMessage: string = '';

  operationType: 'DEBIT' | 'CREDIT' = 'DEBIT';
  amount: number = 0;
  description: string = '';

  constructor(private accountService: AccountService) {}

  searchAccount() {
    this.accountService.getAccount(this.accountId).subscribe({
      next: (data) => {
        this.account = data;
        this.operations = data.accountOperations || []; // Assurez-vous que le backend renvoie ça
        this.errorMessage = '';
      },
      error: (err) => {
        this.account = undefined;
        this.errorMessage = "Compte introuvable";
      }
    });
  }

  executeOperation() {
    const obs = this.operationType === 'DEBIT'
      ? this.accountService.debit(this.accountId, this.amount, this.description)
      : this.accountService.credit(this.accountId, this.amount, this.description);

    obs.subscribe({
      next: () => {
        alert("Opération réussie !");
        this.searchAccount(); // Rafraichir les données
        this.amount = 0;
        this.description = '';
      },
      error: (err) => alert("Erreur: " + err.message)
    });
  }
}
