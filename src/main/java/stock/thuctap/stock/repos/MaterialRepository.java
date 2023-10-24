package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Material;


public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
