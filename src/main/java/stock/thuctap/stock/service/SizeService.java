package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Size;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.SizeDTO;
import stock.thuctap.stock.repos.SizeRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class SizeService {

    private final SizeRepository sizeRepository;
    private final SockDetailRepository sockDetailRepository;

    public SizeService(final SizeRepository sizeRepository,
            final SockDetailRepository sockDetailRepository) {
        this.sizeRepository = sizeRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<SizeDTO> findAll() {
        final List<Size> sizes = sizeRepository.findAll(Sort.by("id"));
        return sizes.stream()
                .map(size -> mapToDTO(size, new SizeDTO()))
                .toList();
    }

    public SizeDTO get(final Integer id) {
        return sizeRepository.findById(id)
                .map(size -> mapToDTO(size, new SizeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SizeDTO sizeDTO) {
        final Size size = new Size();
        mapToEntity(sizeDTO, size);
        return sizeRepository.save(size).getId();
    }

    public void update(final Integer id, final SizeDTO sizeDTO) {
        final Size size = sizeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sizeDTO, size);
        sizeRepository.save(size);
    }

    public void delete(final Integer id) {
        sizeRepository.deleteById(id);
    }

    private SizeDTO mapToDTO(final Size size, final SizeDTO sizeDTO) {
        sizeDTO.setId(size.getId());
        sizeDTO.setCode(size.getCode());
        sizeDTO.setName(size.getName());
        sizeDTO.setStatus(size.getStatus());
        return sizeDTO;
    }

    private Size mapToEntity(final SizeDTO sizeDTO, final Size size) {
        size.setCode(sizeDTO.getCode());
        size.setName(sizeDTO.getName());
        size.setStatus(sizeDTO.getStatus());
        return size;
    }

    public String getReferencedWarning(final Integer id) {
        final Size size = sizeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail sizeSockDetail = sockDetailRepository.findFirstBySize(size);
        if (sizeSockDetail != null) {
            return WebUtils.getMessage("size.sockDetail.size.referenced", sizeSockDetail.getId());
        }
        return null;
    }

}
