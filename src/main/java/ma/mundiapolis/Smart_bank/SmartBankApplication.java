package ma.mundiapolis.Smart_bank;

import ma.mundiapolis.Smart_bank.entities.*;
import ma.mundiapolis.Smart_bank.repositories.BankAccountRepository;
import ma.mundiapolis.Smart_bank.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SmartBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartBankApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository) {
		return args -> {
			Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
				Customer customer = Customer.builder()
						.name(name)
						.email(name + "@gmail.com")
						.build();
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(cust -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);
			});
		};
	}
}