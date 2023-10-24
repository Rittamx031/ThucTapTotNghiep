package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MaterialDTO {

    private Integer id;

    @NotNull
    @Size(max = 100)
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    private Boolean status;

}
