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
import stock.thuctap.stock.model.BlogDTO;
import stock.thuctap.stock.service.BlogService;


@RestController
@RequestMapping(value = "/api/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogResource {

    private final BlogService blogService;

    public BlogResource(final BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable final Integer id) {
        return ResponseEntity.ok(blogService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createBlog(@RequestBody @Valid final BlogDTO blogDTO) {
        final Integer createdId = blogService.create(blogDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBlog(@PathVariable final Integer id,
            @RequestBody @Valid final BlogDTO blogDTO) {
        blogService.update(id, blogDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable final Integer id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
