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
import stock.thuctap.stock.model.CustomerAddressDTO;
import stock.thuctap.stock.service.CustomerAddressService;


@RestController
@RequestMapping(value = "/api/customerAddresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerAddressResource {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressResource(final CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerAddressDTO>> getAllCustomerAddresses() {
        return ResponseEntity.ok(customerAddressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddressDTO> getCustomerAddress(@PathVariable final Integer id) {
        return ResponseEntity.ok(customerAddressService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createCustomerAddress(
            @RequestBody @Valid final CustomerAddressDTO customerAddressDTO) {
        final Integer createdId = customerAddressService.create(customerAddressDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateCustomerAddress(@PathVariable final Integer id,
            @RequestBody @Valid final CustomerAddressDTO customerAddressDTO) {
        customerAddressService.update(id, customerAddressDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable final Integer id) {
        customerAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
