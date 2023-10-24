package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.PayMethod;


public interface PayMethodRepository extends JpaRepository<PayMethod, Integer> {
}
