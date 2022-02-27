package museum.service.repositories;

import museum.service.models.entities.TourStaticContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourStaticContentRepository extends JpaRepository<TourStaticContent, Integer>
{
}
