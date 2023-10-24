package stock.thuctap.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Discount {

        @Id
        @Column(nullable = false, updatable = false)

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false, name = "coupon_code")

        private String couponCode;

        @Column(nullable = false)
        private String name;

        @Column(columnDefinition = "varchar(max)")
        private String description;

        @Column(nullable = false, name = "valid_from")
        private LocalDate validFrom;

        @Column(nullable = false, name = "valid_until")
        private LocalDate validUntil;

        @Column(nullable = false)
        private Integer discountValue;

        @Column
        private Boolean status;

        @OneToMany(mappedBy = "discount")
        private Set<SockDetail> discountSockDetails;

}
