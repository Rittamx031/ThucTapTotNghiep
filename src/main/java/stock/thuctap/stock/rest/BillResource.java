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
import stock.thuctap.stock.model.BillDTO;
import stock.thuctap.stock.service.BillService;


@RestController
@RequestMapping(value = "/api/bills", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillResource {

    private final BillService billService;

    public BillResource(final BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<BillDTO>> getAllBills() {
        return ResponseEntity.ok(billService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> getBill(@PathVariable final Integer id) {
        return ResponseEntity.ok(billService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createBill(@RequestBody @Valid final BillDTO billDTO) {
        final Integer createdId = billService.create(billDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBill(@PathVariable final Integer id,
            @RequestBody @Valid final BillDTO billDTO) {
        billService.update(id, billDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable final Integer id) {
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
