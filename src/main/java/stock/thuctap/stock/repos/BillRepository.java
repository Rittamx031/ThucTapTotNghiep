package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.PayMethod;


public interface BillRepository extends JpaRepository<Bill, Integer> {

    Bill findFirstByEmployee(Employee employee);

    Bill findFirstByCustomer(Customer customer);

    Bill findFirstByPayMethod(PayMethod payMethod);

}
