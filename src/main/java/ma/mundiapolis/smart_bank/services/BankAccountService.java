package ma.mundiapolis.smart_bank.services;

import ma.mundiapolis.smart_bank.entities.*;
import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    List<AccountOperation> accountHistory(String accountId);
}