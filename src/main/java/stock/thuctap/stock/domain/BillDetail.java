package stock.thuctap.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BillDetail {

    @EmbeddedId
    private BillDetailId id;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private Boolean deleted;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "varchar(max)")
    private String note;

    @Column(nullable = false, length = 100)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_bill")
    @JoinColumn(name = "id_bill")
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_sock_detail")
    @JoinColumn(name = "id_sock_detail")
    private SockDetail sockDetail;

}
