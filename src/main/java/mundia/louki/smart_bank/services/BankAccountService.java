package mundia.louki.smart_bank.services;

import mundia.louki.smart_bank.dtos.BankAccountDTO;
import mundia.louki.smart_bank.entities.BankAccount;
import mundia.louki.smart_bank.entities.Customer;
import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    BankAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);

    List<BankAccount> bankAccountList();
}