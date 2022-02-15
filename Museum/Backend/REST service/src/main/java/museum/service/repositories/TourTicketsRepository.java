package museum.service.repositories;

import museum.service.models.entities.TourpurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TourTicketsRepository extends JpaRepository<TourpurchaseEntity, UUID>
{
}
