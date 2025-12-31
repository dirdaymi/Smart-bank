package ma.mundiapolis.smart_bank.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.mundiapolis.smart_bank.entities.*;
import ma.mundiapolis.smart_bank.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new Customer");
        return customerRepository.save(customer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new RuntimeException("Customer not found");
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(currentAccount);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new RuntimeException("Customer not found");
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(savingAccount);
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found"));
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance() < amount)
            throw new RuntimeException("Balance not sufficient");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);

        // Récupérer l'utilisateur connecté (Sécurité)
        String username = "Anonymous";
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        accountOperation.setPerformedBy(username);

        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount = getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);

        String username = "Anonymous";
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        accountOperation.setPerformedBy(username);

        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<AccountOperation> accountHistory(String accountId) {
        return accountOperationRepository.findByBankAccountId(accountId);
    }
}