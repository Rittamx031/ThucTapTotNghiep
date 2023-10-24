package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.ImageSockDetail;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.ImageSockDetailDTO;
import stock.thuctap.stock.repos.ImageSockDetailRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class ImageSockDetailService {

    private final ImageSockDetailRepository imageSockDetailRepository;
    private final SockDetailRepository sockDetailRepository;

    public ImageSockDetailService(final ImageSockDetailRepository imageSockDetailRepository,
            final SockDetailRepository sockDetailRepository) {
        this.imageSockDetailRepository = imageSockDetailRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<ImageSockDetailDTO> findAll() {
        final List<ImageSockDetail> imageSockDetails = imageSockDetailRepository.findAll(Sort.by("id"));
        return imageSockDetails.stream()
                .map(imageSockDetail -> mapToDTO(imageSockDetail, new ImageSockDetailDTO()))
                .toList();
    }

    public ImageSockDetailDTO get(final Integer id) {
        return imageSockDetailRepository.findById(id)
                .map(imageSockDetail -> mapToDTO(imageSockDetail, new ImageSockDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ImageSockDetailDTO imageSockDetailDTO) {
        final ImageSockDetail imageSockDetail = new ImageSockDetail();
        mapToEntity(imageSockDetailDTO, imageSockDetail);
        return imageSockDetailRepository.save(imageSockDetail).getId();
    }

    public void update(final Integer id, final ImageSockDetailDTO imageSockDetailDTO) {
        final ImageSockDetail imageSockDetail = imageSockDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(imageSockDetailDTO, imageSockDetail);
        imageSockDetailRepository.save(imageSockDetail);
    }

    public void delete(final Integer id) {
        imageSockDetailRepository.deleteById(id);
    }

    private ImageSockDetailDTO mapToDTO(final ImageSockDetail imageSockDetail,
            final ImageSockDetailDTO imageSockDetailDTO) {
        imageSockDetailDTO.setId(imageSockDetail.getId());
        imageSockDetailDTO.setPath(imageSockDetail.getPath());
        imageSockDetailDTO.setStatus(imageSockDetail.getStatus());
        return imageSockDetailDTO;
    }

    private ImageSockDetail mapToEntity(final ImageSockDetailDTO imageSockDetailDTO,
            final ImageSockDetail imageSockDetail) {
        imageSockDetail.setPath(imageSockDetailDTO.getPath());
        imageSockDetail.setStatus(imageSockDetailDTO.getStatus());
        return imageSockDetail;
    }

    public String getReferencedWarning(final Integer id) {
        final ImageSockDetail imageSockDetail = imageSockDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SockDetail imageSockDetail2 = sockDetailRepository.findFirstByImage(imageSockDetail);
        if (imageSockDetail != null) {
            return WebUtils.getMessage("imageSockDetail.sockDetail.image.referenced", imageSockDetail2.getId());
        }
        return null;
    }

}
