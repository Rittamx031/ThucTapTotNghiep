package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDTO {

    private Integer id;

    @NotNull
    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 20)
    private String phone;

    @NotNull
    private LocalDate birthday;

    private Boolean status;

}
