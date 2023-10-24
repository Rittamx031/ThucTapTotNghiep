package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.CustomerAddress;


public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {

    CustomerAddress findFirstByCustomer(Customer customer);

}
