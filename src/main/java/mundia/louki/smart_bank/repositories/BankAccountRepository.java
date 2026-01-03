package mundia.louki.smart_bank.repositories;

import mundia.louki.smart_bank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    // Méthode dérivée pour trouver les comptes d'un client spécifique (utile pour la partie Client/Admin)
    List<BankAccount> findByCustomerId(Long customerId);
}