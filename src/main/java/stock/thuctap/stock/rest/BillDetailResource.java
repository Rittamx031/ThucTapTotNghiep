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
import stock.thuctap.stock.model.BillDetailDTO;
import stock.thuctap.stock.service.BillDetailService;


@RestController
@RequestMapping(value = "/api/billDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillDetailResource {

    private final BillDetailService billDetailService;

    public BillDetailResource(final BillDetailService billDetailService) {
        this.billDetailService = billDetailService;
    }

    @GetMapping
    public ResponseEntity<List<BillDetailDTO>> getAllBillDetails() {
        return ResponseEntity.ok(billDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDTO> getBillDetail(@PathVariable final Long id) {
        return ResponseEntity.ok(billDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createBillDetail(
            @RequestBody @Valid final BillDetailDTO billDetailDTO) {
        final Long createdId = billDetailService.create(billDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBillDetail(@PathVariable final Long id,
            @RequestBody @Valid final BillDetailDTO billDetailDTO) {
        billDetailService.update(id, billDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillDetail(@PathVariable final Long id) {
        billDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
