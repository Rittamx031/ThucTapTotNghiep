package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Integer id;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String permissions;

    @NotNull
    @Size(max = 255)
    private String roles;

    private Boolean status;

}
