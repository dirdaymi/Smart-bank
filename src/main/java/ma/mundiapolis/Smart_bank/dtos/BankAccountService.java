package ma.mundiapolis.Smart_bank.services;

import ma.mundiapolis.Smart_bank.entities.BankAccount;
import ma.mundiapolis.Smart_bank.entities.Customer;
import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount getBankAccount(String accountId);
    List<Customer> listCustomers();
    // Méthodes simplifiées pour l'exemple
}