package ma.mundiapolis.Smart_bank.services;

import ma.mundiapolis.Smart_bank.entities.BankAccount;
import ma.mundiapolis.Smart_bank.entities.Customer;
import ma.mundiapolis.Smart_bank.repositories.BankAccountRepository;
import ma.mundiapolis.Smart_bank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }
}