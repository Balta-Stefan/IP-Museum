package museum.service.repositories;

import museum.service.models.entities.EventNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<EventNotificationEntity, Integer>
{
    List<EventNotificationEntity> findAllBySentIsFalse();
}
