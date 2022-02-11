package com.virtualbank.repositories;

import com.virtualbank.models.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, UUID>
{

}
