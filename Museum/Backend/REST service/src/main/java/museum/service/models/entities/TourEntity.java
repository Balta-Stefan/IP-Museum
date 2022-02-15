package museum.service.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tours")
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

    @OneToMany(mappedBy = "tour")
    private List<TourStaticContent> staticContent;

    @OneToMany(mappedBy = "tour")
    private List<TourpurchaseEntity> purchases;

    @ManyToOne
    @JoinColumn(name = "museum", referencedColumnName = "museumID", nullable = false)
    private MuseumEntity museum;
}
