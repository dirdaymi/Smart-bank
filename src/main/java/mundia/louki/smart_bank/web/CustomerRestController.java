package mundia.louki.smart_bank.web;

import lombok.AllArgsConstructor;
import mundia.louki.smart_bank.entities.Customer;
import mundia.louki.smart_bank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<Customer> customers() {
        return bankAccountService.listCustomers();
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return bankAccountService.saveCustomer(customer);
    }
}