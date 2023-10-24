package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Category;
import stock.thuctap.stock.domain.Producer;
import stock.thuctap.stock.domain.Sock;


public interface SockRepository extends JpaRepository<Sock, Integer> {

    Sock findFirstByCategory(Category category);

    Sock findFirstByProducer(Producer producer);

}
