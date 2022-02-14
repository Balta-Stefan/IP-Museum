package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.PresentationResourceType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tourvideos")
public class TourvideoEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "videoID", nullable = false)
    private Integer videoId;

    @Basic
    @Column(name = "videoURI", nullable = false, length = 255)
    private String videoUri;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PresentationResourceType type;

    @ManyToOne
    @JoinColumn(name = "tour", referencedColumnName = "tourID", nullable = false)
    private TourEntity tour;

}
