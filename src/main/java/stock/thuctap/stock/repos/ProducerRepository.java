package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Producer;


public interface ProducerRepository extends JpaRepository<Producer, Integer> {
}
