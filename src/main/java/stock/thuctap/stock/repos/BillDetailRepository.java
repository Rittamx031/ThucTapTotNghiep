package stock.thuctap.stock.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.BillDetail;
import stock.thuctap.stock.domain.BillDetailId;
import stock.thuctap.stock.domain.SockDetail;


public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetailId> {

    BillDetail findFirstByBill(Bill bill);

    BillDetail findFirstBySockDetail(SockDetail sockDetail);

}
