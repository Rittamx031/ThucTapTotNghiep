package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Color;
import stock.thuctap.stock.domain.Discount;
import stock.thuctap.stock.domain.ImageSockDetail;
import stock.thuctap.stock.domain.Material;
import stock.thuctap.stock.domain.Pattern;
import stock.thuctap.stock.domain.Size;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.domain.SockDetail;


public interface SockDetailRepository extends JpaRepository<SockDetail, Integer> {

    SockDetail findFirstBySock(Sock sock);

    SockDetail findFirstByPattern(Pattern pattern);

    SockDetail findFirstByMaterial(Material material);

    SockDetail findFirstBySize(Size size);

    SockDetail findFirstByColor(Color color);

    SockDetail findFirstByDiscount(Discount discount);

    SockDetail findFirstByImage(ImageSockDetail imageSockDetail);

}
