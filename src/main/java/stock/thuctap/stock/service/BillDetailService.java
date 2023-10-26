package stock.thuctap.stock.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.BillDetail;
import stock.thuctap.stock.domain.BillDetailId;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.BillDetailDTO;
import stock.thuctap.stock.repos.BillDetailRepository;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;

@Service
public class BillDetailService {

    private final BillDetailRepository billDetailRepository;
    private final BillRepository billRepository;
    private final SockDetailRepository sockDetailRepository;

    public BillDetailService(final BillDetailRepository billDetailRepository,
            final BillRepository billRepository, final SockDetailRepository sockDetailRepository) {
        this.billDetailRepository = billDetailRepository;
        this.billRepository = billRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<BillDetailDTO> findAll() {
        final List<BillDetail> billDetails = billDetailRepository.findAll(Sort.by("id"));
        return billDetails.stream()
                .map(billDetail -> mapToDTO(billDetail, new BillDetailDTO()))
                .toList();
    }

    public BillDetailDTO get(final BillDetailId id) {
        return billDetailRepository.findById(id)
                .map(billDetail -> mapToDTO(billDetail, new BillDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public BillDetailId create(final BillDetailDTO billDetailDTO) {
        billDetailDTO.setUpdatedAt(LocalDateTime.now());
        final BillDetail billDetail = new BillDetail();
        mapToEntity(billDetailDTO, billDetail);
        return billDetailRepository.save(billDetail).getId();
    }

    public void update(final BillDetailId billDetailId, final BillDetailDTO billDetailDTO) {
        billDetailDTO.setUpdatedAt(LocalDateTime.now());
        final BillDetail billDetail = billDetailRepository.findById(billDetailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(billDetailDTO, billDetail);
        billDetailRepository.save(billDetail);
    }

    public void delete(final BillDetailId id) {
        billDetailRepository.deleteById(id);
    }

    private BillDetailDTO mapToDTO(final BillDetail billDetail, final BillDetailDTO billDetailDTO) {
        billDetailDTO.setUpdatedAt(billDetail.getUpdatedAt());
        billDetailDTO.setDeleted(billDetail.getDeleted());
        billDetailDTO.setQuantity(billDetail.getQuantity());
        billDetailDTO.setPrice(billDetail.getPrice());
        billDetailDTO.setNote(billDetail.getNote());
        billDetailDTO.setStatus(billDetail.getStatus());
        billDetailDTO.setBill(billDetail.getBill() == null ? null : billDetail.getBill().getId());
        billDetailDTO.setSockDetail(billDetail.getSockDetail() == null ? null : billDetail.getSockDetail().getId());
        return billDetailDTO;
    }

    private BillDetail mapToEntity(final BillDetailDTO billDetailDTO, final BillDetail billDetail) {
        billDetail.setId(new BillDetailId(billDetailDTO.getBill(), billDetailDTO.getSockDetail()));
        billDetail.setUpdatedAt(billDetailDTO.getUpdatedAt());
        billDetail.setDeleted(billDetailDTO.getDeleted());
        billDetail.setQuantity(billDetailDTO.getQuantity());
        billDetail.setPrice(billDetailDTO.getPrice());
        billDetail.setNote(billDetailDTO.getNote());
        billDetail.setStatus(billDetailDTO.getStatus());
        final Bill bill = billDetailDTO.getBill() == null ? null
                : billRepository.findById(billDetailDTO.getBill())
                        .orElseThrow(() -> new NotFoundException("bill not found"));
        billDetail.setBill(bill);
        final SockDetail sockDetail = billDetailDTO.getSockDetail() == null ? null
                : sockDetailRepository.findById(billDetailDTO.getSockDetail())
                        .orElseThrow(() -> new NotFoundException("sockDetail not found"));
        billDetail.setSockDetail(sockDetail);
        billDetail.setId(new BillDetailId(bill.getId(), sockDetail.getId()));
        return billDetail;
    }

}
