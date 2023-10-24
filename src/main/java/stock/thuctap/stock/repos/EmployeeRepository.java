package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.Role;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findFirstByRole(Role role);

}
