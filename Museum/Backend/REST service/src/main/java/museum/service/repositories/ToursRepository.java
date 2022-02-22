package museum.service.repositories;

import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ToursRepository extends JpaRepository<TourEntity, Integer>
{
    List<TourEntity> findAllByMuseum(MuseumEntity museum);

    @Query("SELECT t FROM TourEntity t WHERE " +
            "t.museum.museumId=:museumID AND :currentDateTime < t.endTimeStamp")
    List<TourEntity> findAllUnfinishedTours(Integer museumID, LocalDateTime currentDateTime);
}
