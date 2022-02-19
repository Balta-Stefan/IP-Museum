package museum.service.repositories;

import museum.service.models.entities.MuseumTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuseumTypeRepository extends JpaRepository<MuseumTypeEntity, Integer>
{
    Optional<MuseumTypeEntity> findByTypeIgnoreCase(String type);
}
