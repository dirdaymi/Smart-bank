import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../../_services/account.service';
import { BankAccount } from '../../../_models/core-models';

@Component({
    selector: 'app-accounts',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './accounts.html'
})
export class AccountsComponent implements OnInit {
    accounts: BankAccount[] = [];
    errorMessage: string = '';
    currentPage: number = 0;
    pageSize: number = 5;

    constructor(private accountService: AccountService) {}

    ngOnInit() {
        this.loadAccounts();
    }

    loadAccounts() {
        this.accountService.getAllAccounts(this.currentPage, this.pageSize).subscribe({
            next: (data) => {
                // Adaptez selon le format renvoyé par Spring Boot (Page vs List)
                // Si c'est une Page, les données sont souvent dans data.content ou data.bankAccounts
                this.accounts = Array.isArray(data) ? data : (data.content || data.bankAccounts || []);
            },
            error: (err) => this.errorMessage = "Impossible de charger les comptes (Vérifiez le Backend)"
        });
    }
}