package museum.service.repositories;

import museum.service.models.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToursRepository extends JpaRepository<TourEntity, Integer>
{
}
