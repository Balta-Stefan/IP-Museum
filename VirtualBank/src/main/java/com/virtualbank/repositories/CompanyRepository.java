package com.virtualbank.repositories;

import com.virtualbank.models.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>
{
    Optional<CompanyEntity> findByName(String name);
}
