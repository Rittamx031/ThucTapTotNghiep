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
import stock.thuctap.stock.model.ProducerDTO;
import stock.thuctap.stock.service.ProducerService;


@RestController
@RequestMapping(value = "/api/producers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerResource {

    private final ProducerService producerService;

    public ProducerResource(final ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<List<ProducerDTO>> getAllProducers() {
        return ResponseEntity.ok(producerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerDTO> getProducer(@PathVariable final Integer id) {
        return ResponseEntity.ok(producerService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProducer(
            @RequestBody @Valid final ProducerDTO producerDTO) {
        final Integer createdId = producerService.create(producerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProducer(@PathVariable final Integer id,
            @RequestBody @Valid final ProducerDTO producerDTO) {
        producerService.update(id, producerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable final Integer id) {
        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
