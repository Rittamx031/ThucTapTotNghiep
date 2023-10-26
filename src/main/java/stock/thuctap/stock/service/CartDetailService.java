package stock.thuctap.stock.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Cart;
import stock.thuctap.stock.domain.CartDetail;
import stock.thuctap.stock.domain.CartDetailId;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.CartDetailDTO;
import stock.thuctap.stock.repos.CartDetailRepository;
import stock.thuctap.stock.repos.CartRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;

@Service
public class CartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final SockDetailRepository sockDetailRepository;

    public CartDetailService(final CartDetailRepository cartDetailRepository,
            final CartRepository cartRepository, final SockDetailRepository sockDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<CartDetailDTO> findAll() {
        final List<CartDetail> cartDetails = cartDetailRepository.findAll(Sort.by("id"));
        return cartDetails.stream()
                .map(cartDetail -> mapToDTO(cartDetail, new CartDetailDTO()))
                .toList();
    }

    public CartDetailDTO get(final CartDetailId id) {
        return cartDetailRepository.findById(id)
                .map(cartDetail -> mapToDTO(cartDetail, new CartDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public CartDetailId create(final CartDetailDTO cartDetailDTO) {
        cartDetailDTO.setUpdatedAt(LocalDateTime.now());
        final CartDetail cartDetail = new CartDetail();
        mapToEntity(cartDetailDTO, cartDetail);
        return cartDetailRepository.save(cartDetail).getId();
    }

    public void update(final CartDetailId id, final CartDetailDTO cartDetailDTO) {
        cartDetailDTO.setUpdatedAt(LocalDateTime.now());
        final CartDetail cartDetail = cartDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDetailDTO, cartDetail);
        cartDetailRepository.save(cartDetail);
    }

    public void delete(final CartDetailId id) {
        cartDetailRepository.deleteById(id);
    }

    private CartDetailDTO mapToDTO(final CartDetail cartDetail, final CartDetailDTO cartDetailDTO) {
        cartDetailDTO.setUpdatedAt(cartDetail.getUpdatedAt());
        cartDetailDTO.setDeleted(cartDetail.getDeleted());
        cartDetailDTO.setQuantity(cartDetail.getQuantity());
        cartDetailDTO.setNote(cartDetail.getNote());
        cartDetailDTO.setStatus(cartDetail.getStatus());
        cartDetailDTO.setCart(cartDetail.getCart() == null ? null : cartDetail.getCart().getId());
        cartDetailDTO.setSockDetail(cartDetail.getSockDetail() == null ? null : cartDetail.getSockDetail().getId());
        return cartDetailDTO;
    }

    private CartDetail mapToEntity(final CartDetailDTO cartDetailDTO, final CartDetail cartDetail) {
        cartDetail.setUpdatedAt(cartDetailDTO.getUpdatedAt());
        cartDetail.setDeleted(cartDetailDTO.getDeleted());
        cartDetail.setQuantity(cartDetailDTO.getQuantity());
        cartDetail.setNote(cartDetailDTO.getNote());
        cartDetail.setStatus(cartDetailDTO.getStatus());
        final Cart cart = cartDetailDTO.getCart() == null ? null
                : cartRepository.findById(cartDetailDTO.getCart())
                        .orElseThrow(() -> new NotFoundException("cart not found"));
        cartDetail.setCart(cart);
        final SockDetail sockDetail = cartDetailDTO.getSockDetail() == null ? null
                : sockDetailRepository.findById(cartDetailDTO.getSockDetail())
                        .orElseThrow(() -> new NotFoundException("sockDetail not found"));
        cartDetail.setSockDetail(sockDetail);
        cartDetail.setId(new CartDetailId(cart.getId(), sockDetail.getId()));
        return cartDetail;
    }

}
