package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.StaticResourceLocationType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TourStaticContent")
public class TourStaticContent
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "staticContentID", nullable = false)
    private Integer staticContentID;

    @Basic
    @Column(name = "URI", nullable = false, length = 255)
    private String URI;

    @Basic
    @Column(name = "locationType", nullable = false)
    @Enumerated(EnumType.STRING)
    private StaticResourceLocationType locationType;

    @ManyToOne
    @JoinColumn(name = "tour", referencedColumnName = "tourID", nullable = false)
    private TourEntity tour;

    @Basic
    @Column(name = "resourceType", nullable = false)
    @Enumerated(EnumType.STRING)
    private StaticResourceLocationType resourceType;
}
