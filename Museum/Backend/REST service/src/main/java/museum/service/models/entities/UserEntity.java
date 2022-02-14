package museum.service.models.entities;

import lombok.*;
import museum.service.models.enums.Roles;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
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

    @OneToMany(mappedBy = "user")
    private List<AccesstokenEntity> accessTokens;

    @OneToMany(mappedBy = "user")
    private List<TourpurchaseEntity> purchasedTours;

}
