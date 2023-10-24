package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Producer;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.model.ProducerDTO;
import stock.thuctap.stock.repos.ProducerRepository;
import stock.thuctap.stock.repos.SockRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final SockRepository sockRepository;

    public ProducerService(final ProducerRepository producerRepository,
            final SockRepository sockRepository) {
        this.producerRepository = producerRepository;
        this.sockRepository = sockRepository;
    }

    public List<ProducerDTO> findAll() {
        final List<Producer> producers = producerRepository.findAll(Sort.by("id"));
        return producers.stream()
                .map(producer -> mapToDTO(producer, new ProducerDTO()))
                .toList();
    }

    public ProducerDTO get(final Integer id) {
        return producerRepository.findById(id)
                .map(producer -> mapToDTO(producer, new ProducerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProducerDTO producerDTO) {
        final Producer producer = new Producer();
        mapToEntity(producerDTO, producer);
        return producerRepository.save(producer).getId();
    }

    public void update(final Integer id, final ProducerDTO producerDTO) {
        final Producer producer = producerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(producerDTO, producer);
        producerRepository.save(producer);
    }

    public void delete(final Integer id) {
        producerRepository.deleteById(id);
    }

    private ProducerDTO mapToDTO(final Producer producer, final ProducerDTO producerDTO) {
        producerDTO.setId(producer.getId());
        producerDTO.setCode(producer.getCode());
        producerDTO.setName(producer.getName());
        producerDTO.setStatus(producer.getStatus());
        return producerDTO;
    }

    private Producer mapToEntity(final ProducerDTO producerDTO, final Producer producer) {
        producer.setCode(producerDTO.getCode());
        producer.setName(producerDTO.getName());
        producer.setStatus(producerDTO.getStatus());
        return producer;
    }

    public String getReferencedWarning(final Integer id) {
        final Producer producer = producerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Sock producerSock = sockRepository.findFirstByProducer(producer);
        if (producerSock != null) {
            return WebUtils.getMessage("producer.sock.producer.referenced", producerSock.getId());
        }
        return null;
    }

}
