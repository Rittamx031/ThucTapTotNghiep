package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Color;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.ColorDTO;
import stock.thuctap.stock.repos.ColorRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class ColorService {

    private final ColorRepository colorRepository;
    private final SockDetailRepository sockDetailRepository;

    public ColorService(final ColorRepository colorRepository,
            final SockDetailRepository sockDetailRepository) {
        this.colorRepository = colorRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<ColorDTO> findAll() {
        final List<Color> colors = colorRepository.findAll(Sort.by("id"));
        return colors.stream()
                .map(color -> mapToDTO(color, new ColorDTO()))
                .toList();
    }

    public ColorDTO get(final Integer id) {
        return colorRepository.findById(id)
                .map(color -> mapToDTO(color, new ColorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ColorDTO colorDTO) {
        final Color color = new Color();
        mapToEntity(colorDTO, color);
        return colorRepository.save(color).getId();
    }

    public void update(final Integer id, final ColorDTO colorDTO) {
        final Color color = colorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(colorDTO, color);
        colorRepository.save(color);
    }

    public void delete(final Integer id) {
        colorRepository.deleteById(id);
    }

    private ColorDTO mapToDTO(final Color color, final ColorDTO colorDTO) {
        colorDTO.setId(color.getId());
        colorDTO.setCode(color.getCode());
        colorDTO.setName(color.getName());
        colorDTO.setStatus(color.getStatus());
        return colorDTO;
    }

    private Color mapToEntity(final ColorDTO colorDTO, final Color color) {
        color.setCode(colorDTO.getCode());
        color.setName(colorDTO.getName());
        color.setStatus(colorDTO.getStatus());
        return color;
    }

    public String getReferencedWarning(final Integer id) {
        final Color color = colorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail colorSockDetail = sockDetailRepository.findFirstByColor(color);
        if (colorSockDetail != null) {
            return WebUtils.getMessage("color.sockDetail.color.referenced", colorSockDetail.getId());
        }
        return null;
    }

}
