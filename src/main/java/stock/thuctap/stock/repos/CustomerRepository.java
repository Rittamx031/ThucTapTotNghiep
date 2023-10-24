package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
