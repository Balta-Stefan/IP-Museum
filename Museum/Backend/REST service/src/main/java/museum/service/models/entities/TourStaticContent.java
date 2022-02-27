package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.StaticResourceType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TourStaticContent")
public class TourStaticContent
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tourStaticContentID", nullable = false)
    private Integer staticContentID;

    @Basic
    @Column(name = "URI", nullable = false, length = 255)
    private String URI;

    @Basic
    @Column(name = "isYouTubeVideo")
    private Boolean isYouTubeVideo;

    @ManyToOne
    @JoinColumn(name = "tour", referencedColumnName = "tourID", nullable = false)
    private TourEntity tour;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StaticResourceType resourceType;
}
