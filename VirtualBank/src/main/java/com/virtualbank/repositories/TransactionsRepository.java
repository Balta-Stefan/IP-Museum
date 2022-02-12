package com.virtualbank.repositories;

import com.virtualbank.models.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, UUID>
{
    @Query("SELECT ts FROM TransactionEntity ts WHERE ts.payer.id=:id")
    List<TransactionEntity> findAllByPayer(Integer id);
}
