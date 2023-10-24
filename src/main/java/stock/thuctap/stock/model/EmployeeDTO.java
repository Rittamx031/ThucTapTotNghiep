package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDTO {

    private Integer id;

    @NotNull
    private LocalDateTime updatedAt;

    private Boolean deleted;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String birthday;

    @NotNull
    @Size(max = 20)
    private String phone;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private String password;

    private Boolean status;

    private Integer role;

}
