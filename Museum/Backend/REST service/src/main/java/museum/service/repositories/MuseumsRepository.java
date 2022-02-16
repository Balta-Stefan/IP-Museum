package museum.service.repositories;

import museum.service.models.entities.MuseumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MuseumsRepository extends JpaRepository<MuseumEntity, Integer>
{
    @Query("SELECT m FROM MuseumEntity m WHERE " +
            "(:name IS NULL OR m.name=:name) AND" +
            "(:city IS NULL OR m.city=:city)")
    List<MuseumEntity> filterMuseums(String name, String city);
}
