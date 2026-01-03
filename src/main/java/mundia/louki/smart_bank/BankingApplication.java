package mundia.louki.smart_bank;

import mundia.louki.smart_bank.entities.Customer;
import mundia.louki.smart_bank.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {
			// 1. Création de clients
			Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});

			// 2. Création de comptes pour chaque client
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					// Compte Courant avec 9000 de solde et 500 de découvert
					bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
					// Compte Épargne avec 120000 de solde et 5.5% d'intérêt
					bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			// Note: Les IDs des comptes sont des UUID (ex: "550e8400-e29b..."),
			// il faudra les copier depuis la console ou Swagger pour les tester.
		};
	}
}