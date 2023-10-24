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
import stock.thuctap.stock.model.ColorDTO;
import stock.thuctap.stock.service.ColorService;


@RestController
@RequestMapping(value = "/api/colors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ColorResource {

    private final ColorService colorService;

    public ColorResource(final ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public ResponseEntity<List<ColorDTO>> getAllColors() {
        return ResponseEntity.ok(colorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorDTO> getColor(@PathVariable final Integer id) {
        return ResponseEntity.ok(colorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createColor(@RequestBody @Valid final ColorDTO colorDTO) {
        final Integer createdId = colorService.create(colorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateColor(@PathVariable final Integer id,
            @RequestBody @Valid final ColorDTO colorDTO) {
        colorService.update(id, colorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable final Integer id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
