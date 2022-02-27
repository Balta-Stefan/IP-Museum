package museum.service.repositories;

import museum.service.models.entities.MuseumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MuseumsRepository extends PagingAndSortingRepository<MuseumEntity, Integer>
{
    @Query("SELECT m FROM MuseumEntity m WHERE " +
			"(:name IS NULL OR m.name=:name) AND" +
            "(:country IS NULL OR m.country=:country) AND" +
            "(:city IS NULL OR m.city=:city)")
    List<MuseumEntity> filterMuseums(String name, String country, String city);
}
