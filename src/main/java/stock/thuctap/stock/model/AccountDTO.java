package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountDTO {

    private Integer id;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private String password;

    private Boolean status;

    private Integer customer;

}
