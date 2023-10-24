package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDetailDTO {

    private Long id;

    @NotNull
    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    private Integer quantity;

    private String note;

    @NotNull
    @Size(max = 100)
    private String status;

    private Integer cart;

    private Integer sockDetail;

}
