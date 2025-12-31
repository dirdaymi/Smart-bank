package ma.mundiapolis.smart_bank.repositories;
import ma.mundiapolis.smart_bank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String keyword);
}