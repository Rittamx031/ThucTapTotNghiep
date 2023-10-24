package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Color;


public interface ColorRepository extends JpaRepository<Color, Integer> {
}
