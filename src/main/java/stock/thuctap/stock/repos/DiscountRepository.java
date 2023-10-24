package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Discount;


public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
