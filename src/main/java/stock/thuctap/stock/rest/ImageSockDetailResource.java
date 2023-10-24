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
import stock.thuctap.stock.model.ImageSockDetailDTO;
import stock.thuctap.stock.service.ImageSockDetailService;


@RestController
@RequestMapping(value = "/api/imageSockDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageSockDetailResource {

    private final ImageSockDetailService imageSockDetailService;

    public ImageSockDetailResource(final ImageSockDetailService imageSockDetailService) {
        this.imageSockDetailService = imageSockDetailService;
    }

    @GetMapping
    public ResponseEntity<List<ImageSockDetailDTO>> getAllImageSockDetails() {
        return ResponseEntity.ok(imageSockDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageSockDetailDTO> getImageSockDetail(@PathVariable final Integer id) {
        return ResponseEntity.ok(imageSockDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createImageSockDetail(
            @RequestBody @Valid final ImageSockDetailDTO imageSockDetailDTO) {
        final Integer createdId = imageSockDetailService.create(imageSockDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateImageSockDetail(@PathVariable final Integer id,
            @RequestBody @Valid final ImageSockDetailDTO imageSockDetailDTO) {
        imageSockDetailService.update(id, imageSockDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageSockDetail(@PathVariable final Integer id) {
        imageSockDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
