package museum.service.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EventNotifications")
@AllArgsConstructor
@NoArgsConstructor
public class EventNotificationEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourID", updatable = false)
    private TourEntity tour;

    @Basic
    @Column(name = "sent", nullable = false)
    private Boolean sent;

    @Basic
    @Column(name = "sendDateTime", nullable = false)
    private LocalDateTime sendDateTime;

    @Override
    public String toString()
    {
        return "EventNotificationEntity{" +
                "id=" + id +
                ", tourID=" + tour.getTourId() +
                ", sent=" + sent +
                ", sendDateTime=" + sendDateTime +
                '}';
    }
}
