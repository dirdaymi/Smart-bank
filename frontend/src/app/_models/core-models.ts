export interface Customer {
  id: number;
  name: string;
  email: string;
}

export interface BankAccount {
  id: string; // C'est un UUID (String) côté Java
  type: 'CA' | 'SA'; // CurrentAccount ou SavingAccount
  balance: number;
  createdAt: Date;
  status: string;
  customer?: Customer; // Attention à la récursion ici (gérée côté backend)
  overDraft?: number;   // Spécifique CA
  interestRate?: number; // Spécifique SA
  accountOperations?: AccountOperation[];
}

export interface AccountOperation {
  id: number;
  operationDate: Date;
  amount: number;
  type: 'DEBIT' | 'CREDIT';
  description: string;
}

export interface DashboardStats {
  totalUsers: number;
  totalTransactions: number;
  totalFunds: number;
}
