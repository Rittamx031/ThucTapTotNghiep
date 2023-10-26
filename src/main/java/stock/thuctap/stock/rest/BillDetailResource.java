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

import stock.thuctap.stock.domain.BillDetailId;
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

    @GetMapping("/{idbill}/{idproduct}")
    public ResponseEntity<BillDetailDTO> getBillDetail(@PathVariable("idbill") final Integer idbill,
            @PathVariable("idproduct") final Integer idproduct) {
        return ResponseEntity.ok(billDetailService.get(new BillDetailId(idbill, idproduct)));
    }

    @PostMapping
    public ResponseEntity<BillDetailId> createBillDetail(
            @RequestBody @Valid final BillDetailDTO billDetailDTO) {
        final BillDetailId createdId = billDetailService.create(billDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{idbill}/{idproduct}")
    public ResponseEntity<BillDetailId> updateBillDetail(@PathVariable("idbill") final Integer idbill,
            @PathVariable("idproduct") final Integer idproduct,
            @RequestBody @Valid final BillDetailDTO billDetailDTO) {
        billDetailService.update(new BillDetailId(idbill, idproduct), billDetailDTO);
        return ResponseEntity.ok(new BillDetailId(idbill, idproduct));
    }

    @DeleteMapping("/{idbill}/{idproduct}")
    public ResponseEntity<Void> deleteBillDetail(@PathVariable("idbill") final Integer idbill,
            @PathVariable("idproduct") final Integer idproduct) {
        billDetailService.delete(new BillDetailId(idbill, idproduct));
        return ResponseEntity.noContent().build();
    }

}
