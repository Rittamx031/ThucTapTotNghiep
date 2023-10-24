package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BillDTO {

    private Integer id;

    @NotNull
    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    private LocalDate dateCreate;

    @NotNull
    private LocalDate datePayment;

    private String note;

    @NotNull
    @Size(max = 100)
    private String status;

    private Integer employee;

    private Integer customer;

    private Integer payMethod;

}
