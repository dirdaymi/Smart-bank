package ma.mundiapolis.smart_bank.tools;

import dev.langchain4j.agent.tool.Tool;
import ma.mundiapolis.smart_bank.entities.AccountOperation;
import ma.mundiapolis.smart_bank.entities.BankAccount;
import ma.mundiapolis.smart_bank.repositories.AccountOperationRepository;
import ma.mundiapolis.smart_bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankingTools {

    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;

    public BankingTools(BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @Tool("Retourne le solde d'un compte donné son ID")
    public String getAccountBalance(String accountId) {
        System.out.println("--- Tool called: getAccountBalance for " + accountId);
        return bankAccountRepository.findById(accountId)
                .map(acc -> String.valueOf(acc.getBalance()))
                .orElse("Compte introuvable");
    }

    @Tool("Retourne les 5 dernières opérations d'un compte")
    public String getLastOperations(String accountId) {
        System.out.println("--- Tool called: getLastOperations for " + accountId);
        List<AccountOperation> ops = accountOperationRepository.findByBankAccountId(accountId);
        if(ops.isEmpty()) return "Aucune opération.";
        // On prend les 5 dernières
        return ops.stream()
                .limit(5)
                .map(op -> op.getType() + " de " + op.getAmount() + " le " + op.getOperationDate())
                .collect(Collectors.joining("\n"));
    }
}