package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Category;
import stock.thuctap.stock.domain.Producer;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.SockDTO;
import stock.thuctap.stock.repos.CategoryRepository;
import stock.thuctap.stock.repos.ProducerRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.repos.SockRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class SockService {

    private final SockRepository sockRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final SockDetailRepository sockDetailRepository;

    public SockService(final SockRepository sockRepository,
            final CategoryRepository categoryRepository,
            final ProducerRepository producerRepository,
            final SockDetailRepository sockDetailRepository) {
        this.sockRepository = sockRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<SockDTO> findAll() {
        final List<Sock> socks = sockRepository.findAll(Sort.by("id"));
        return socks.stream()
                .map(sock -> mapToDTO(sock, new SockDTO()))
                .toList();
    }

    public SockDTO get(final Integer id) {
        return sockRepository.findById(id)
                .map(sock -> mapToDTO(sock, new SockDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SockDTO sockDTO) {
        final Sock sock = new Sock();
        mapToEntity(sockDTO, sock);
        return sockRepository.save(sock).getId();
    }

    public void update(final Integer id, final SockDTO sockDTO) {
        final Sock sock = sockRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sockDTO, sock);
        sockRepository.save(sock);
    }

    public void delete(final Integer id) {
        sockRepository.deleteById(id);
    }

    private SockDTO mapToDTO(final Sock sock, final SockDTO sockDTO) {
        sockDTO.setId(sock.getId());
        sockDTO.setCode(sock.getCode());
        sockDTO.setName(sock.getName());
        sockDTO.setDescription(sock.getDescription());
        sockDTO.setPath(sock.getPath());
        sockDTO.setStatus(sock.getStatus());
        sockDTO.setCategory(sock.getCategory() == null ? null : sock.getCategory().getId());
        sockDTO.setProducer(sock.getProducer() == null ? null : sock.getProducer().getId());
        return sockDTO;
    }

    private Sock mapToEntity(final SockDTO sockDTO, final Sock sock) {
        sock.setCode(sockDTO.getCode());
        sock.setName(sockDTO.getName());
        sock.setDescription(sockDTO.getDescription());
        sock.setPath(sockDTO.getPath());
        sock.setStatus(sockDTO.getStatus());
        final Category category = sockDTO.getCategory() == null ? null : categoryRepository.findById(sockDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        sock.setCategory(category);
        final Producer producer = sockDTO.getProducer() == null ? null : producerRepository.findById(sockDTO.getProducer())
                .orElseThrow(() -> new NotFoundException("producer not found"));
        sock.setProducer(producer);
        return sock;
    }

    public String getReferencedWarning(final Integer id) {
        final Sock sock = sockRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail sockSockDetail = sockDetailRepository.findFirstBySock(sock);
        if (sockSockDetail != null) {
            return WebUtils.getMessage("sock.sockDetail.sock.referenced", sockSockDetail.getId());
        }
        return null;
    }

}
