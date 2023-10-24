package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Category;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.model.CategoryDTO;
import stock.thuctap.stock.repos.CategoryRepository;
import stock.thuctap.stock.repos.SockRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SockRepository sockRepository;

    public CategoryService(final CategoryRepository categoryRepository,
            final SockRepository sockRepository) {
        this.categoryRepository = categoryRepository;
        this.sockRepository = sockRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Integer id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Integer id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Integer id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setCode(category.getCode());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setCode(categoryDTO.getCode());
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return category;
    }

    public String getReferencedWarning(final Integer id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Sock categorySock = sockRepository.findFirstByCategory(category);
        if (categorySock != null) {
            return WebUtils.getMessage("category.sock.category.referenced", categorySock.getId());
        }
        return null;
    }

}
