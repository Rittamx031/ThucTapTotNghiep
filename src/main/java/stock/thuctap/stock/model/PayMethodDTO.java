package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayMethodDTO {

    private Integer id;

    @NotNull
    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    @Size(max = 255)
    private String name;

    private Boolean status;

}
