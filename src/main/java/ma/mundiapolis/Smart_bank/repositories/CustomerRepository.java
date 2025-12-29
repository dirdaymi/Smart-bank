package ma.mundiapolis.Smart_bank.repositories;
import ma.mundiapolis.Smart_bank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}