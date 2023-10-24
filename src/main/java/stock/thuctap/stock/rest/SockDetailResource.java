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
import stock.thuctap.stock.model.SockDetailDTO;
import stock.thuctap.stock.service.SockDetailService;


@RestController
@RequestMapping(value = "/api/sockDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class SockDetailResource {

    private final SockDetailService sockDetailService;

    public SockDetailResource(final SockDetailService sockDetailService) {
        this.sockDetailService = sockDetailService;
    }

    @GetMapping
    public ResponseEntity<List<SockDetailDTO>> getAllSockDetails() {
        return ResponseEntity.ok(sockDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SockDetailDTO> getSockDetail(@PathVariable final Integer id) {
        return ResponseEntity.ok(sockDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createSockDetail(
            @RequestBody @Valid final SockDetailDTO sockDetailDTO) {
        final Integer createdId = sockDetailService.create(sockDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSockDetail(@PathVariable final Integer id,
            @RequestBody @Valid final SockDetailDTO sockDetailDTO) {
        sockDetailService.update(id, sockDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSockDetail(@PathVariable final Integer id) {
        sockDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
