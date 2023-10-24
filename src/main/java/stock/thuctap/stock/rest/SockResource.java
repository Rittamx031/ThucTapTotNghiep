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
import stock.thuctap.stock.model.SockDTO;
import stock.thuctap.stock.service.SockService;


@RestController
@RequestMapping(value = "/api/socks", produces = MediaType.APPLICATION_JSON_VALUE)
public class SockResource {

    private final SockService sockService;

    public SockResource(final SockService sockService) {
        this.sockService = sockService;
    }

    @GetMapping
    public ResponseEntity<List<SockDTO>> getAllSocks() {
        return ResponseEntity.ok(sockService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SockDTO> getSock(@PathVariable final Integer id) {
        return ResponseEntity.ok(sockService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createSock(@RequestBody @Valid final SockDTO sockDTO) {
        final Integer createdId = sockService.create(sockDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSock(@PathVariable final Integer id,
            @RequestBody @Valid final SockDTO sockDTO) {
        sockService.update(id, sockDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSock(@PathVariable final Integer id) {
        sockService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
