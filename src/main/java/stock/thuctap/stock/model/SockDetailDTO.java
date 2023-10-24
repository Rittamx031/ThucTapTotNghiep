package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SockDetailDTO {

    private Integer id;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double unitBasePrice;

    private Boolean status;

    private Integer sock;

    private Integer pattern;

    private Integer material;

    private Integer size;

    private Integer color;

    private Integer discount;

    private Integer image;

}
