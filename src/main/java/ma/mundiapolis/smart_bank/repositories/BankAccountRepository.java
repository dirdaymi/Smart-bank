package ma.mundiapolis.smart_bank.repositories;
import ma.mundiapolis.smart_bank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}