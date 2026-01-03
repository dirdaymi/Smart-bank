export interface User {
  id: number;
  name: string;
  email: string;
  roles?: string[];
  // bankAccounts?: BankAccount[]; // Optional depending on JSON depth
}
