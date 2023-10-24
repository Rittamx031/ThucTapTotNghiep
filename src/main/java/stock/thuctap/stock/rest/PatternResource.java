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
import stock.thuctap.stock.model.PatternDTO;
import stock.thuctap.stock.service.PatternService;


@RestController
@RequestMapping(value = "/api/patterns", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatternResource {

    private final PatternService patternService;

    public PatternResource(final PatternService patternService) {
        this.patternService = patternService;
    }

    @GetMapping
    public ResponseEntity<List<PatternDTO>> getAllPatterns() {
        return ResponseEntity.ok(patternService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatternDTO> getPattern(@PathVariable final Integer id) {
        return ResponseEntity.ok(patternService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createPattern(@RequestBody @Valid final PatternDTO patternDTO) {
        final Integer createdId = patternService.create(patternDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePattern(@PathVariable final Integer id,
            @RequestBody @Valid final PatternDTO patternDTO) {
        patternService.update(id, patternDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePattern(@PathVariable final Integer id) {
        patternService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
