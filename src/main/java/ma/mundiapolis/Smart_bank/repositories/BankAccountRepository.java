package ma.mundiapolis.Smart_bank.repositories;
import ma.mundiapolis.Smart_bank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {}