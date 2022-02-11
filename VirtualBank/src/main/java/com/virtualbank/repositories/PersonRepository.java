package com.virtualbank.repositories;

import com.virtualbank.models.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer>
{
    Optional<PersonEntity> findByCardNumber(String cardNumber);
}
