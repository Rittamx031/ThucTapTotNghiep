package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
