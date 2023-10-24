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
import stock.thuctap.stock.model.SizeDTO;
import stock.thuctap.stock.service.SizeService;


@RestController
@RequestMapping(value = "/api/sizes", produces = MediaType.APPLICATION_JSON_VALUE)
public class SizeResource {

    private final SizeService sizeService;

    public SizeResource(final SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public ResponseEntity<List<SizeDTO>> getAllSizes() {
        return ResponseEntity.ok(sizeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDTO> getSize(@PathVariable final Integer id) {
        return ResponseEntity.ok(sizeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createSize(@RequestBody @Valid final SizeDTO sizeDTO) {
        final Integer createdId = sizeService.create(sizeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSize(@PathVariable final Integer id,
            @RequestBody @Valid final SizeDTO sizeDTO) {
        sizeService.update(id, sizeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable final Integer id) {
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
