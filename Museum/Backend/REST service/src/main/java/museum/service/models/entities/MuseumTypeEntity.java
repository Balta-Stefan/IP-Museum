package museum.service.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "MuseumTypes")
public class MuseumTypeEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "museumTypeID", nullable = false)
    private Integer museumTypeID;

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @OneToMany(mappedBy = "museumType")
    private List<MuseumEntity> museums;
}
