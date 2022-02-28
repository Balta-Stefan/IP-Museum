package museum.service.models.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "museums")
@EntityListeners(AuditingEntityListener.class)
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
    @Column(name = "thumbnail", length = 255)
    private String thumbnail;

    @Basic
    @Column(name = "countryAlpha2Code", length = 2, nullable = false)
    private String countryAlpha2Code;

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


    @OneToMany(mappedBy = "museum")
    private List<TourEntity> tours;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "museumTypeID", nullable = false)
    private MuseumTypeEntity museumType;
}
