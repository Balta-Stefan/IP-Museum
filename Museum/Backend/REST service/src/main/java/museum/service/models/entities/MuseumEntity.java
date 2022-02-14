package museum.service.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "museums")
public class MuseumEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "museumID", nullable = false)
    private Integer museumId;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Basic
    @Column(name = "address", nullable = false, length = 45)
    private String address;

    @Basic
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Basic
    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Basic
    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @Basic
    @Column(name = "latitude", nullable = false, precision = 8)
    private BigDecimal latitude;

    @Basic
    @Column(name = "longitude", nullable = false, precision = 8)
    private BigDecimal longitude;

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @OneToMany(mappedBy = "museum")
    private List<TourEntity> tours;

}
