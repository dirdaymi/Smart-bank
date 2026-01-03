package mundia.louki.smart_bank.repositories;

import mundia.louki.smart_bank.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    // Permet de récupérer toutes les opérations liées à un compte bancaire précis
    // Spring Data JPA génère la requête SQL automatiquement : SELECT * FROM AccountOperationModel WHERE bank_account_id = ?
    List<AccountOperation> findByBankAccountId(String accountId);

    // Variante triée (optionnelle mais recommandée pour l'affichage) : Les opérations les plus récentes en premier
    List<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId);
}
