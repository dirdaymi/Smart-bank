package mundia.louki.smart_bank.web;

import lombok.AllArgsConstructor;
import mundia.louki.smart_bank.dtos.CreditDTO;
import mundia.louki.smart_bank.dtos.DebitDTO;
import mundia.louki.smart_bank.dtos.TransferRequestDTO;
import mundia.louki.smart_bank.entities.BankAccount;
import mundia.louki.smart_bank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts") // Standardize route
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    @GetMapping // <--- On retire le chemin, ça prendra celui de la classe par défaut
    public List<BankAccount> listAccounts() {
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/{accountId}")
    public BankAccount getBankAccount(@PathVariable String accountId) {
        return bankAccountService.getBankAccount(accountId);
    }

    // List accounts by customer (Essential for Frontend Profile)
    // Note: You need to add findByCustomerId in Service first or use Repository directly
    // For now, assuming you add listCustomerAccounts to Service:
    // @GetMapping("/customer/{customerId}")
    // public List<BankAccount> listAccounts(@PathVariable Long customerId) { ... }

    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO request) {
        bankAccountService.credit(request.getAccountId(), request.getAmount(), request.getDescription());
        return request;
    }

    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO request) {
        bankAccountService.debit(request.getAccountId(), request.getAmount(), request.getDescription());
        return request;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequestDTO request) {
        bankAccountService.transfer(
                request.getAccountSource(),
                request.getAccountDestination(),
                request.getAmount()
        );
    }
}