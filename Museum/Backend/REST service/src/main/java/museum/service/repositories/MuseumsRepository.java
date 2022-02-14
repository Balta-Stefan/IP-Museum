package museum.service.repositories;

import museum.service.models.entities.MuseumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumsRepository extends JpaRepository<MuseumEntity, Integer>
{
}
