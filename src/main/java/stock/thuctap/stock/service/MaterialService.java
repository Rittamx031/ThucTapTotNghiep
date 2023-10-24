package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Material;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.MaterialDTO;
import stock.thuctap.stock.repos.MaterialRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final SockDetailRepository sockDetailRepository;

    public MaterialService(final MaterialRepository materialRepository,
            final SockDetailRepository sockDetailRepository) {
        this.materialRepository = materialRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<MaterialDTO> findAll() {
        final List<Material> materials = materialRepository.findAll(Sort.by("id"));
        return materials.stream()
                .map(material -> mapToDTO(material, new MaterialDTO()))
                .toList();
    }

    public MaterialDTO get(final Integer id) {
        return materialRepository.findById(id)
                .map(material -> mapToDTO(material, new MaterialDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MaterialDTO materialDTO) {
        final Material material = new Material();
        mapToEntity(materialDTO, material);
        return materialRepository.save(material).getId();
    }

    public void update(final Integer id, final MaterialDTO materialDTO) {
        final Material material = materialRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(materialDTO, material);
        materialRepository.save(material);
    }

    public void delete(final Integer id) {
        materialRepository.deleteById(id);
    }

    private MaterialDTO mapToDTO(final Material material, final MaterialDTO materialDTO) {
        materialDTO.setId(material.getId());
        materialDTO.setCode(material.getCode());
        materialDTO.setName(material.getName());
        materialDTO.setStatus(material.getStatus());
        return materialDTO;
    }

    private Material mapToEntity(final MaterialDTO materialDTO, final Material material) {
        material.setCode(materialDTO.getCode());
        material.setName(materialDTO.getName());
        material.setStatus(materialDTO.getStatus());
        return material;
    }

    public String getReferencedWarning(final Integer id) {
        final Material material = materialRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail materialSockDetail = sockDetailRepository.findFirstByMaterial(material);
        if (materialSockDetail != null) {
            return WebUtils.getMessage("material.sockDetail.material.referenced", materialSockDetail.getId());
        }
        return null;
    }

}
