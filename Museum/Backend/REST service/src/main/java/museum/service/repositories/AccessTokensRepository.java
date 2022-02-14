package museum.service.repositories;

import museum.service.models.entities.AccesstokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccessTokensRepository extends JpaRepository<AccesstokenEntity, UUID>
{

}
