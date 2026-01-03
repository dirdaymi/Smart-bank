import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccount } from '../_models/core-models';

@Injectable({ providedIn: 'root' })
export class AccountService {
  // 👇 CORRECTION : On retire '/api' pour correspondre à votre backend
  // Essayez cette URL qui est le standard généré par Spring pour l'entité BankAccount
  private baseUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  // Méthode pour lister TOUS les comptes (utilisée par la page Comptes)
  getAllAccounts(page: number = 0, size: number = 5): Observable<any> {
    // On tente avec pagination
    return this.http.get<any>(`${this.baseUrl}?page=${page}&size=${size}`);
  }

  // Méthode pour chercher un compte spécifique (utilisée par Opérations)
  getAccount(accountId: string): Observable<BankAccount> {
    return this.http.get<BankAccount>(`${this.baseUrl}/${accountId}`);
  }

  // Méthode pour les comptes d'un client (utilisée par Profil/User Management)
  getAccountsByCustomer(customerId: number): Observable<BankAccount[]> {
    // Attention : Vérifiez si votre backend utilise /accounts/customer/{id} ou autre
    // Si ça échoue, essayez : `${this.baseUrl}/search/customer?id=${customerId}`
    return this.http.get<BankAccount[]>(`${this.baseUrl}/customer/${customerId}`);
  }

  // Opérations Débit/Crédit
  debit(accountId: string, amount: number, description: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/debit`, { accountId, amount, description });
  }

  credit(accountId: string, amount: number, description: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/credit`, { accountId, amount, description });
  }

  transfer(accountSource: string, accountDest: string, amount: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/transfer`, { accountSource, accountDest, amount });
  }

  // Création d'un compte (Sauvegarder un compte)
  createAccount(customerId: number, balance: number, type: 'CA' | 'SA'): Observable<BankAccount> {
    return this.http.post<BankAccount>(`${this.baseUrl}`, { customerId, balance, type });
  }
}