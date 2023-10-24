package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.CartDetail;
import stock.thuctap.stock.domain.SockDetail;


public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    CartDetail findFirstBySockDetail(SockDetail sockDetail);

}
