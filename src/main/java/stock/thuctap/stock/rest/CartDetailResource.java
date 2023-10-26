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

import stock.thuctap.stock.domain.CartDetailId;
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

    @GetMapping("/{idcart}/{idproduct}")
    public ResponseEntity<CartDetailDTO> getCartDetail(@PathVariable("idcart") final Long idcart,
            @PathVariable("idproduct") final Long idproduct) {
        return ResponseEntity.ok(cartDetailService.get(new CartDetailId(idcart, idproduct)));
    }

    @PostMapping
    public ResponseEntity<CartDetailId> createCartDetail(
            @RequestBody @Valid final CartDetailDTO cartDetailDTO) {
        final CartDetailId createdId = cartDetailService.create(cartDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{idcart}/{idproduct}")
    public ResponseEntity<CartDetailId> updateCartDetail(@PathVariable("idcart") final Long idcart,
            @PathVariable("idproduct") final Long idproduct,
            @RequestBody @Valid final CartDetailDTO cartDetailDTO) {
        cartDetailService.update(new CartDetailId(idcart, idproduct), cartDetailDTO);
        return ResponseEntity.ok(new CartDetailId(idcart, idproduct));
    }

    @DeleteMapping("/{idcart}/{idproduct}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable("idcart") final Long idcart,
            @PathVariable("idproduct") final Long idproduct) {
        cartDetailService.delete(new CartDetailId(idcart, idproduct));
        return ResponseEntity.noContent().build();
    }

}
