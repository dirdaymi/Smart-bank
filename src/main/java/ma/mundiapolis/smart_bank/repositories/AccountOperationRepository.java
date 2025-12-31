package ma.mundiapolis.smart_bank.repositories;
import ma.mundiapolis.smart_bank.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
}