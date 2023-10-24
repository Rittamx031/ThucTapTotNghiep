package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImageSockDetailDTO {

    private Integer id;

    @NotNull
    private String path;

    private Boolean status;

}
