package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DiscountDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String couponCode;

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validUntil;

    @NotNull
    private Integer discountValue;

    private Boolean status;

}
