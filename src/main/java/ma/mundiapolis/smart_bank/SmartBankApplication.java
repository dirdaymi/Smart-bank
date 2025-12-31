package ma.mundiapolis.smart_bank;

import ma.mundiapolis.smart_bank.entities.*;
import ma.mundiapolis.smart_bank.repositories.*;
import ma.mundiapolis.smart_bank.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class SmartBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartBankApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService,
							CustomerRepository customerRepository) {
		return args -> {
			// Création clients
			Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});

			// Création comptes
			customerRepository.findAll().forEach(cust -> {
				CurrentAccount ca = bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, cust.getId());
				SavingAccount sa = bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, cust.getId());

				// Opérations factices
				bankAccountService.credit(ca.getId(), 10000 + Math.random()*120000, "Credit Initial");
				bankAccountService.debit(ca.getId(), 1000 + Math.random()*9000, "Facture Internet");

				System.out.println("Compte créé ID: " + ca.getId() + " pour " + cust.getName());
			});
		};
	}
}