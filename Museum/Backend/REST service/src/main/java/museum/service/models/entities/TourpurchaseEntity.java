package museum.service.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tourpurchases")
public class TourpurchaseEntity
{
    @GeneratedValue
    @Id
    @Column(name = "purchaseID", nullable = false)
    private UUID purchaseId;

    @Basic
    @Column(name = "purchased", nullable = false)
    private LocalDateTime purchased;

    @Basic
    @Column(name = "paid")
    private LocalDateTime paid;

    @Basic
    @Column(name = "paymentURL", nullable = true, length = 255)
    private String paymentURL;

    @ManyToOne
    @JoinColumn(name = "tour", referencedColumnName = "tourID", nullable = false)
    private TourEntity tour;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "userID", nullable = false)
    private UserEntity user;

}
