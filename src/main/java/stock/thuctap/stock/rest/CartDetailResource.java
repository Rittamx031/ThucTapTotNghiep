package stock.thuctap.stock.rest;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.thuctap.stock.model.CartDetailDTO;
import stock.thuctap.stock.service.CartDetailService;


@RestController
@RequestMapping(value = "/api/cartDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartDetailResource {

    private final CartDetailService cartDetailService;

    public CartDetailResource(final CartDetailService cartDetailService) {
        this.cartDetailService = cartDetailService;
    }

    @GetMapping
    public ResponseEntity<List<CartDetailDTO>> getAllCartDetails() {
        return ResponseEntity.ok(cartDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDetailDTO> getCartDetail(@PathVariable final Long id) {
        return ResponseEntity.ok(cartDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCartDetail(
            @RequestBody @Valid final CartDetailDTO cartDetailDTO) {
        final Long createdId = cartDetailService.create(cartDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCartDetail(@PathVariable final Long id,
            @RequestBody @Valid final CartDetailDTO cartDetailDTO) {
        cartDetailService.update(id, cartDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable final Long id) {
        cartDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
