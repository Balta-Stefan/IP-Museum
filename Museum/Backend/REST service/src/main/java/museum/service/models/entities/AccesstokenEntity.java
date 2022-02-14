package museum.service.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "accesstokens")
public class AccesstokenEntity
{
    @GeneratedValue
    @Id
    @Column(name = "token", nullable = false)
    private UUID token;

    @Basic
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Basic
    @Column(name = "validUntil", nullable = false)
    private LocalDateTime validUntil;

    @Basic
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "userID", nullable = false)
    private UserEntity user;

}
