package stock.thuctap.stock.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.BillDetail;
import stock.thuctap.stock.domain.Blog;
import stock.thuctap.stock.domain.CartDetail;
import stock.thuctap.stock.domain.Color;
import stock.thuctap.stock.domain.Discount;
import stock.thuctap.stock.domain.ImageSockDetail;
import stock.thuctap.stock.domain.Material;
import stock.thuctap.stock.domain.Pattern;
import stock.thuctap.stock.domain.Size;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.SockDetailDTO;
import stock.thuctap.stock.repos.BillDetailRepository;
import stock.thuctap.stock.repos.BlogRepository;
import stock.thuctap.stock.repos.CartDetailRepository;
import stock.thuctap.stock.repos.ColorRepository;
import stock.thuctap.stock.repos.DiscountRepository;
import stock.thuctap.stock.repos.ImageSockDetailRepository;
import stock.thuctap.stock.repos.MaterialRepository;
import stock.thuctap.stock.repos.PatternRepository;
import stock.thuctap.stock.repos.SizeRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.repos.SockRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
@Transactional
public class SockDetailService {

    private final SockDetailRepository sockDetailRepository;
    private final SockRepository sockRepository;
    private final PatternRepository patternRepository;
    private final MaterialRepository materialRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final DiscountRepository discountRepository;
    private final ImageSockDetailRepository imageSockDetailRepository;
    private final BlogRepository blogRepository;
    private final CartDetailRepository cartDetailRepository;
    private final BillDetailRepository billDetailRepository;

    public SockDetailService(final SockDetailRepository sockDetailRepository,
            final SockRepository sockRepository, final PatternRepository patternRepository,
            final MaterialRepository materialRepository, final SizeRepository sizeRepository,
            final ColorRepository colorRepository, final DiscountRepository discountRepository,
            final ImageSockDetailRepository imageSockDetailRepository,
            final BlogRepository blogRepository, final CartDetailRepository cartDetailRepository,
            final BillDetailRepository billDetailRepository) {
        this.sockDetailRepository = sockDetailRepository;
        this.sockRepository = sockRepository;
        this.patternRepository = patternRepository;
        this.materialRepository = materialRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.discountRepository = discountRepository;
        this.imageSockDetailRepository = imageSockDetailRepository;
        this.blogRepository = blogRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.billDetailRepository = billDetailRepository;
    }

    public List<SockDetailDTO> findAll() {
        final List<SockDetail> sockDetails = sockDetailRepository.findAll(Sort.by("id"));
        return sockDetails.stream()
                .map(sockDetail -> mapToDTO(sockDetail, new SockDetailDTO()))
                .toList();
    }

    public SockDetailDTO get(final Integer id) {
        return sockDetailRepository.findById(id)
                .map(sockDetail -> mapToDTO(sockDetail, new SockDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SockDetailDTO sockDetailDTO) {
        final SockDetail sockDetail = new SockDetail();
        mapToEntity(sockDetailDTO, sockDetail);
        return sockDetailRepository.save(sockDetail).getId();
    }

    public void update(final Integer id, final SockDetailDTO sockDetailDTO) {
        final SockDetail sockDetail = sockDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sockDetailDTO, sockDetail);
        sockDetailRepository.save(sockDetail);
    }

    public void delete(final Integer id) {
        final SockDetail sockDetail = sockDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        blogRepository.findAllBySockDetailBlogSockDetails(sockDetail)
                .forEach(blog -> blog.getSockDetailBlogSockDetails().remove(sockDetail));
        sockDetailRepository.delete(sockDetail);
    }

    private SockDetailDTO mapToDTO(final SockDetail sockDetail, final SockDetailDTO sockDetailDTO) {
        sockDetailDTO.setId(sockDetail.getId());
        sockDetailDTO.setQuantity(sockDetail.getQuantity());
        sockDetailDTO.setUnitBasePrice(sockDetail.getUnitBasePrice());
        sockDetailDTO.setStatus(sockDetail.getStatus());
        sockDetailDTO.setSock(sockDetail.getSock() == null ? null : sockDetail.getSock().getId());
        sockDetailDTO.setPattern(sockDetail.getPattern() == null ? null : sockDetail.getPattern().getId());
        sockDetailDTO.setMaterial(sockDetail.getMaterial() == null ? null : sockDetail.getMaterial().getId());
        sockDetailDTO.setSize(sockDetail.getSize() == null ? null : sockDetail.getSize().getId());
        sockDetailDTO.setColor(sockDetail.getColor() == null ? null : sockDetail.getColor().getId());
        sockDetailDTO.setDiscount(sockDetail.getDiscount() == null ? null : sockDetail.getDiscount().getId());
        sockDetailDTO.setImage(sockDetail.getImage() == null ? null : sockDetail.getImage().getId());
        return sockDetailDTO;
    }

    private SockDetail mapToEntity(final SockDetailDTO sockDetailDTO, final SockDetail sockDetail) {
        sockDetail.setQuantity(sockDetailDTO.getQuantity());
        sockDetail.setUnitBasePrice(sockDetailDTO.getUnitBasePrice());
        sockDetail.setStatus(sockDetailDTO.getStatus());
        final Sock sock = sockDetailDTO.getSock() == null ? null : sockRepository.findById(sockDetailDTO.getSock())
                .orElseThrow(() -> new NotFoundException("sock not found"));
        sockDetail.setSock(sock);
        final Pattern pattern = sockDetailDTO.getPattern() == null ? null : patternRepository.findById(sockDetailDTO.getPattern())
                .orElseThrow(() -> new NotFoundException("pattern not found"));
        sockDetail.setPattern(pattern);
        final Material material = sockDetailDTO.getMaterial() == null ? null : materialRepository.findById(sockDetailDTO.getMaterial())
                .orElseThrow(() -> new NotFoundException("material not found"));
        sockDetail.setMaterial(material);
        final Size size = sockDetailDTO.getSize() == null ? null : sizeRepository.findById(sockDetailDTO.getSize())
                .orElseThrow(() -> new NotFoundException("size not found"));
        sockDetail.setSize(size);
        final Color color = sockDetailDTO.getColor() == null ? null : colorRepository.findById(sockDetailDTO.getColor())
                .orElseThrow(() -> new NotFoundException("color not found"));
        sockDetail.setColor(color);
        final Discount discount = sockDetailDTO.getDiscount() == null ? null : discountRepository.findById(sockDetailDTO.getDiscount())
                .orElseThrow(() -> new NotFoundException("discount not found"));
        sockDetail.setDiscount(discount);
        final ImageSockDetail image = sockDetailDTO.getImage() == null ? null : imageSockDetailRepository.findById(sockDetailDTO.getImage())
                .orElseThrow(() -> new NotFoundException("image not found"));
        sockDetail.setImage(image);
        return sockDetail;
    }

    public String getReferencedWarning(final Integer id) {
        final SockDetail sockDetail = sockDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Blog sockDetailBlogSockDetailsBlog = blogRepository.findFirstBySockDetailBlogSockDetails(sockDetail);
        if (sockDetailBlogSockDetailsBlog != null) {
            return WebUtils.getMessage("sockDetail.blog.sockDetailBlogSockDetails.referenced", sockDetailBlogSockDetailsBlog.getId());
        }
        final CartDetail sockDetailCartDetail = cartDetailRepository.findFirstBySockDetail(sockDetail);
        if (sockDetailCartDetail != null) {
            return WebUtils.getMessage("sockDetail.cartDetail.sockDetail.referenced", sockDetailCartDetail.getId());
        }
        final BillDetail sockDetailBillDetail = billDetailRepository.findFirstBySockDetail(sockDetail);
        if (sockDetailBillDetail != null) {
            return WebUtils.getMessage("sockDetail.billDetail.sockDetail.referenced", sockDetailBillDetail.getId());
        }
        return null;
    }

}
