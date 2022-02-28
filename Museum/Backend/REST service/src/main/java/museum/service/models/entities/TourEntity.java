package museum.service.models.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tours")
@EntityListeners(AuditingEntityListener.class)
public class TourEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tourID", nullable = false)
    private Integer tourId;

    @Basic
    @Column(name = "startTimestamp", nullable = false)
    private LocalDateTime startTimestamp;

    @Basic
    @Column(name = "endTimeStamp", nullable = false)
    private LocalDateTime endTimeStamp;

    @Basic
    @Column(name = "price", nullable = false, precision = 4)
    private BigDecimal price;

    @Basic
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", updatable = false)
    private UserEntity createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy")
    private UserEntity updatedBy;


    @OneToMany(mappedBy = "tour")
    private List<TourStaticContent> staticContent;

    @OneToMany(mappedBy = "tour")
    private List<TourpurchaseEntity> purchases;

    @ManyToOne
    @JoinColumn(name = "museum", referencedColumnName = "museumID", nullable = false)
    private MuseumEntity museum;
}
