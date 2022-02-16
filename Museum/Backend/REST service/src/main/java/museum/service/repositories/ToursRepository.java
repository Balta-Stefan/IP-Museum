package museum.service.repositories;

import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToursRepository extends JpaRepository<TourEntity, Integer>
{
    List<TourEntity> findAllByMuseum(MuseumEntity museum);
}
