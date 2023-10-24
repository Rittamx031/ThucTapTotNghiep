package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Discount;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.DiscountDTO;
import stock.thuctap.stock.repos.DiscountRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final SockDetailRepository sockDetailRepository;

    public DiscountService(final DiscountRepository discountRepository,
            final SockDetailRepository sockDetailRepository) {
        this.discountRepository = discountRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<DiscountDTO> findAll() {
        final List<Discount> discounts = discountRepository.findAll(Sort.by("id"));
        return discounts.stream()
                .map(discount -> mapToDTO(discount, new DiscountDTO()))
                .toList();
    }

    public DiscountDTO get(final Integer id) {
        return discountRepository.findById(id)
                .map(discount -> mapToDTO(discount, new DiscountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DiscountDTO discountDTO) {
        final Discount discount = new Discount();
        mapToEntity(discountDTO, discount);
        return discountRepository.save(discount).getId();
    }

    public void update(final Integer id, final DiscountDTO discountDTO) {
        final Discount discount = discountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(discountDTO, discount);
        discountRepository.save(discount);
    }

    public void delete(final Integer id) {
        discountRepository.deleteById(id);
    }

    private DiscountDTO mapToDTO(final Discount discount, final DiscountDTO discountDTO) {
        discountDTO.setId(discount.getId());
        discountDTO.setCouponCode(discount.getCouponCode());
        discountDTO.setName(discount.getName());
        discountDTO.setDescription(discount.getDescription());
        discountDTO.setValidFrom(discount.getValidFrom());
        discountDTO.setValidUntil(discount.getValidUntil());
        discountDTO.setDiscountValue(discount.getDiscountValue());
        discountDTO.setStatus(discount.getStatus());
        return discountDTO;
    }

    private Discount mapToEntity(final DiscountDTO discountDTO, final Discount discount) {
        discount.setCouponCode(discountDTO.getCouponCode());
        discount.setName(discountDTO.getName());
        discount.setDescription(discountDTO.getDescription());
        discount.setValidFrom(discountDTO.getValidFrom());
        discount.setValidUntil(discountDTO.getValidUntil());
        discount.setDiscountValue(discountDTO.getDiscountValue());
        discount.setStatus(discountDTO.getStatus());
        return discount;
    }

    public String getReferencedWarning(final Integer id) {
        final Discount discount = discountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail discountSockDetail = sockDetailRepository.findFirstByDiscount(discount);
        if (discountSockDetail != null) {
            return WebUtils.getMessage("discount.sockDetail.discount.referenced", discountSockDetail.getId());
        }
        return null;
    }

}
