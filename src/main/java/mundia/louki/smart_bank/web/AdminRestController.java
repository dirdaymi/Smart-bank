package mundia.louki.smart_bank.web;

import mundia.louki.smart_bank.entities.*;
import mundia.louki.smart_bank.repositories.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Indispensable pour Angular
public class AdminRestController {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    // CORRECTION : On utilise AccountOperationRepository
    private final AccountOperationRepository operationRepository;

    public AdminRestController(CustomerRepository customerRepository,
                               BankAccountRepository bankAccountRepository,
                               AccountOperationRepository operationRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
    }

    // 1. Liste des utilisateurs
    @GetMapping("/users")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 2. Liste des transactions (Correction du type de retour)
    @GetMapping("/transactions")
    public List<AccountOperation> getAllTransactions() {
        return operationRepository.findAll();
    }

    // 3. Statistiques
    @GetMapping("/stats")
    public java.util.Map<String, Object> getStats() {
        long totalUsers = customerRepository.count();
        long totalTx = operationRepository.count(); // Correction ici aussi

        // Calcul somme des soldes (attention aux nulls potentiels)
        double totalFunds = bankAccountRepository.findAll().stream()
                .mapToDouble(acc -> acc.getBalance() != 0 ? acc.getBalance() : 0.0)
                .sum();

        return java.util.Map.of(
                "totalUsers", totalUsers,
                "totalTransactions", totalTx,
                "totalFunds", totalFunds
        );
    }
}