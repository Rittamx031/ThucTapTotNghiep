package stock.thuctap.stock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlogDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    private String path;

    @NotNull
    private LocalDate dateCreate;

    private LocalDate dateUpdate;

    @NotNull
    private String content;

    private Boolean status;

    private List<Integer> sockDetailBlogSockDetails;

}
