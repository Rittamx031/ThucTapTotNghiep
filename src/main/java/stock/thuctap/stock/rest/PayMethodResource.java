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
import stock.thuctap.stock.model.PayMethodDTO;
import stock.thuctap.stock.service.PayMethodService;


@RestController
@RequestMapping(value = "/api/payMethods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayMethodResource {

    private final PayMethodService payMethodService;

    public PayMethodResource(final PayMethodService payMethodService) {
        this.payMethodService = payMethodService;
    }

    @GetMapping
    public ResponseEntity<List<PayMethodDTO>> getAllPayMethods() {
        return ResponseEntity.ok(payMethodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayMethodDTO> getPayMethod(@PathVariable final Integer id) {
        return ResponseEntity.ok(payMethodService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createPayMethod(
            @RequestBody @Valid final PayMethodDTO payMethodDTO) {
        final Integer createdId = payMethodService.create(payMethodDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePayMethod(@PathVariable final Integer id,
            @RequestBody @Valid final PayMethodDTO payMethodDTO) {
        payMethodService.update(id, payMethodDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayMethod(@PathVariable final Integer id) {
        payMethodService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
