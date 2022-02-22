package museum.service.repositories;

import museum.service.models.entities.TourpurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TourTicketsRepository extends JpaRepository<TourpurchaseEntity, UUID>
{
    @Query("SELECT t FROM TourpurchaseEntity t WHERE " +
            "t.tour.tourId=:tourID AND t.user.userId=:userID")
    Optional<TourpurchaseEntity> findTicket(Integer tourID, Integer userID);
}
