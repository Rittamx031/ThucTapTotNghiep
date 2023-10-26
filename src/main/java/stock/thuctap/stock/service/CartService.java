package stock.thuctap.stock.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.domain.Cart;
import stock.thuctap.stock.domain.CartDetail;
import stock.thuctap.stock.model.CartDTO;
import stock.thuctap.stock.repos.AccountRepository;
import stock.thuctap.stock.repos.CartDetailRepository;
import stock.thuctap.stock.repos.CartRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(final CartRepository cartRepository,
            final AccountRepository accountRepository,
            final CartDetailRepository cartDetailRepository) {
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("id"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Integer id) {
        return cartRepository.findById(id)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CartDTO cartDTO) {
        cartDTO.setDateCreate(LocalDateTime.now());
        cartDTO.setUpdatedAt(LocalDateTime.now());
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getId();
    }

    public void update(final Integer id, final CartDTO cartDTO) {
        cartDTO.setUpdatedAt(LocalDateTime.now());
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Integer id) {
        cartRepository.deleteById(id);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setId(cart.getId());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());
        cartDTO.setDeleted(cart.getDeleted());
        cartDTO.setDateCreate(cart.getDateCreate());
        cartDTO.setNote(cart.getNote());
        cartDTO.setStatus(cart.getStatus());
        cartDTO.setAccount(cart.getAccount() == null ? null : cart.getAccount().getId());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        cart.setUpdatedAt(cartDTO.getUpdatedAt());
        cart.setDeleted(cartDTO.getDeleted());
        cart.setDateCreate(cartDTO.getDateCreate());
        cart.setNote(cartDTO.getNote());
        cart.setStatus(cartDTO.getStatus());
        final Account account = cartDTO.getAccount() == null ? null
                : accountRepository.findById(cartDTO.getAccount())
                        .orElseThrow(() -> new NotFoundException("account not found"));
        cart.setAccount(account);
        return cart;
    }

    public String getReferencedWarning(final Integer id) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CartDetail cartCartDetail = cartDetailRepository.findFirstByCart(cart);
        if (cartCartDetail != null) {
            return WebUtils.getMessage("cart.cartDetail.cart.referenced", cartCartDetail.getId());
        }
        return null;
    }

}
