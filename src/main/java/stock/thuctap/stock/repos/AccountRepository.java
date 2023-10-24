package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.domain.Customer;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findFirstByCustomer(Customer customer);

}
