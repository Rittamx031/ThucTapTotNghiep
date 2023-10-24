package stock.thuctap.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Blog {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "varchar(max)")
    private String path;

    @Column(nullable = false,name="date_create")
    private LocalDate dateCreate;

    @Column(name = "date_update")
    private LocalDate dateUpdate;

    @Column(nullable = false, columnDefinition = "varchar(max)")
    private String content;

    @Column
    private Boolean status;

    @ManyToMany
    @JoinTable(
            name = "SockDetail_Blog",
            joinColumns = @JoinColumn(name = "id_blog"),
            inverseJoinColumns = @JoinColumn(name = "id_sock_detail")
    )
    private Set<SockDetail> sockDetailBlogSockDetails;

}
