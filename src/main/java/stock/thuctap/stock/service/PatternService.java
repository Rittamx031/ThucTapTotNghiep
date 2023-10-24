package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Pattern;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.PatternDTO;
import stock.thuctap.stock.repos.PatternRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class PatternService {

    private final PatternRepository patternRepository;
    private final SockDetailRepository sockDetailRepository;

    public PatternService(final PatternRepository patternRepository,
            final SockDetailRepository sockDetailRepository) {
        this.patternRepository = patternRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<PatternDTO> findAll() {
        final List<Pattern> patterns = patternRepository.findAll(Sort.by("id"));
        return patterns.stream()
                .map(pattern -> mapToDTO(pattern, new PatternDTO()))
                .toList();
    }

    public PatternDTO get(final Integer id) {
        return patternRepository.findById(id)
                .map(pattern -> mapToDTO(pattern, new PatternDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PatternDTO patternDTO) {
        final Pattern pattern = new Pattern();
        mapToEntity(patternDTO, pattern);
        return patternRepository.save(pattern).getId();
    }

    public void update(final Integer id, final PatternDTO patternDTO) {
        final Pattern pattern = patternRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(patternDTO, pattern);
        patternRepository.save(pattern);
    }

    public void delete(final Integer id) {
        patternRepository.deleteById(id);
    }

    private PatternDTO mapToDTO(final Pattern pattern, final PatternDTO patternDTO) {
        patternDTO.setId(pattern.getId());
        patternDTO.setCode(pattern.getCode());
        patternDTO.setName(pattern.getName());
        patternDTO.setStatus(pattern.getStatus());
        return patternDTO;
    }

    private Pattern mapToEntity(final PatternDTO patternDTO, final Pattern pattern) {
        pattern.setCode(patternDTO.getCode());
        pattern.setName(patternDTO.getName());
        pattern.setStatus(patternDTO.getStatus());
        return pattern;
    }

    public String getReferencedWarning(final Integer id) {
        final Pattern pattern = patternRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail patternSockDetail = sockDetailRepository.findFirstByPattern(pattern);
        if (patternSockDetail != null) {
            return WebUtils.getMessage("pattern.sockDetail.pattern.referenced", patternSockDetail.getId());
        }
        return null;
    }

}
