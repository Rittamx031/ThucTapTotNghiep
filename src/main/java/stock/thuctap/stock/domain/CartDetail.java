package stock.thuctap.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartDetail {

    @EmbeddedId
    private CartDetailId id;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private Boolean deleted;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "varchar(max)")
    private String note;

    @Column(nullable = false, length = 100)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sock_detail")
    private SockDetail sockDetail;

}
