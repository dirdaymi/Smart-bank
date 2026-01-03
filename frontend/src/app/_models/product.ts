export interface BankAccount {
  id: string;
  type: string; // 'CA' or 'SA'
  balance: number;
  createdAt: Date;
  status: string;
  overDraft?: number;   // Only for CurrentAccount
  interestRate?: number; // Only for SavingAccount
}
