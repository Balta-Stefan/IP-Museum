package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.PresentationResourceType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tourpictures")
public class TourpictureEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pictureID", nullable = false)
    private Integer pictureId;

    @Basic
    @Column(name = "pictureURI", nullable = false, length = 255)
    private String pictureUri;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PresentationResourceType type;

    @ManyToOne
    @JoinColumn(name = "tour", referencedColumnName = "tourID", nullable = false)
    private TourEntity tour;

}
