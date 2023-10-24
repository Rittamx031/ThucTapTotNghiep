package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Pattern;


public interface PatternRepository extends JpaRepository<Pattern, Integer> {
}
