package stock.thuctap.stock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetailDTO {

    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    private Integer quantity;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    private String note;

    @NotNull
    @Size(max = 100)
    private String status;

    private Integer bill;

    private Integer sockDetail;

}
