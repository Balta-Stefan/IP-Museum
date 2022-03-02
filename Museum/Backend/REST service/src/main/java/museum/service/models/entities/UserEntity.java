package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.Roles;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Basic
    @Column(name = "username", nullable = false, unique = true, length = 45)
    private String username;

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Basic
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy")
    @LastModifiedBy
    private UserEntity updatedBy;




    @OneToMany(mappedBy = "user")
    private List<AccesstokenEntity> accessTokens;

    @OneToMany(mappedBy = "user")
    private List<TourpurchaseEntity> purchasedTours;

}
