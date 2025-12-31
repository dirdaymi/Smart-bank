package ma.mundiapolis.smart_bank.web;

import ma.mundiapolis.smart_bank.dtos.CreditDTO;
import ma.mundiapolis.smart_bank.dtos.DebitDTO;
import ma.mundiapolis.smart_bank.entities.AccountOperation;
import ma.mundiapolis.smart_bank.entities.BankAccount;
import ma.mundiapolis.smart_bank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccount getBankAccount(@PathVariable String accountId){
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperation> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO){
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO){
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
}