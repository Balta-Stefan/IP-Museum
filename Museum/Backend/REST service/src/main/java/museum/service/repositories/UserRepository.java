package museum.service.repositories;

import museum.service.models.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndUsername(String email, String username);

    Optional<UserEntity> findByUsernameAndActiveTrue(String username);

    Page<UserEntity> findAllByActiveIsFalse(Pageable pageable);
}
