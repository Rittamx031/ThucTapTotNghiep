package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.PayMethod;
import stock.thuctap.stock.model.PayMethodDTO;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.PayMethodRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class PayMethodService {

    private final PayMethodRepository payMethodRepository;
    private final BillRepository billRepository;

    public PayMethodService(final PayMethodRepository payMethodRepository,
            final BillRepository billRepository) {
        this.payMethodRepository = payMethodRepository;
        this.billRepository = billRepository;
    }

    public List<PayMethodDTO> findAll() {
        final List<PayMethod> payMethods = payMethodRepository.findAll(Sort.by("id"));
        return payMethods.stream()
                .map(payMethod -> mapToDTO(payMethod, new PayMethodDTO()))
                .toList();
    }

    public PayMethodDTO get(final Integer id) {
        return payMethodRepository.findById(id)
                .map(payMethod -> mapToDTO(payMethod, new PayMethodDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PayMethodDTO payMethodDTO) {
        final PayMethod payMethod = new PayMethod();
        mapToEntity(payMethodDTO, payMethod);
        return payMethodRepository.save(payMethod).getId();
    }

    public void update(final Integer id, final PayMethodDTO payMethodDTO) {
        final PayMethod payMethod = payMethodRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(payMethodDTO, payMethod);
        payMethodRepository.save(payMethod);
    }

    public void delete(final Integer id) {
        payMethodRepository.deleteById(id);
    }

    private PayMethodDTO mapToDTO(final PayMethod payMethod, final PayMethodDTO payMethodDTO) {
        payMethodDTO.setId(payMethod.getId());
        payMethodDTO.setUpdatedAt(payMethod.getUpdatedAt());
        payMethodDTO.setDeleted(payMethod.getDeleted());
        payMethodDTO.setName(payMethod.getName());
        payMethodDTO.setStatus(payMethod.getStatus());
        return payMethodDTO;
    }

    private PayMethod mapToEntity(final PayMethodDTO payMethodDTO, final PayMethod payMethod) {
        payMethod.setUpdatedAt(payMethodDTO.getUpdatedAt());
        payMethod.setDeleted(payMethodDTO.getDeleted());
        payMethod.setName(payMethodDTO.getName());
        payMethod.setStatus(payMethodDTO.getStatus());
        return payMethod;
    }

    public String getReferencedWarning(final Integer id) {
        final PayMethod payMethod = payMethodRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Bill payMethodBill = billRepository.findFirstByPayMethod(payMethod);
        if (payMethodBill != null) {
            return WebUtils.getMessage("payMethod.bill.payMethod.referenced", payMethodBill.getId());
        }
        return null;
    }

}
