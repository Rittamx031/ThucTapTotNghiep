package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.domain.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findFirstByAccount(Account account);

}
