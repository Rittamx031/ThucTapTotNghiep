package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
}
