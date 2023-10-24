package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SockDTO {

    private Integer id;

    @NotNull
    @Size(max = 100)
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String path;

    private Boolean status;

    private Integer category;

    private Integer producer;

}
