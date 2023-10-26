package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {

    private Integer id;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    private LocalDateTime dateCreate;

    private String note;

    
    private Integer status;

    private Integer account;

}
