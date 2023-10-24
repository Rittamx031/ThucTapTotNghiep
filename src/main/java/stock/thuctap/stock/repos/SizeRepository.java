package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Size;


public interface SizeRepository extends JpaRepository<Size, Integer> {
}
