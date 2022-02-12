package com.virtualbank.services.impl;

import com.virtualbank.exceptions.InsufficientFunds;
import com.virtualbank.exceptions.NotFoundException;
import com.virtualbank.exceptions.TransactionAlreadyDone;
import com.virtualbank.models.PaymentDTO;
import com.virtualbank.models.TransactionDTO;
import com.virtualbank.models.entities.CompanyEntity;
import com.virtualbank.models.entities.PersonEntity;
import com.virtualbank.models.entities.TransactionEntity;
import com.virtualbank.models.requests.PaymentRequest;
import com.virtualbank.models.responses.PaymentRequestResponse;
import com.virtualbank.repositories.CompanyRepository;
import com.virtualbank.repositories.PersonRepository;
import com.virtualbank.repositories.TransactionsRepository;
import com.virtualbank.services.PaymentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService
{
    private final TransactionsRepository transactionsRepository;
    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Value("${redirect.url.base}")
    private String baseURL;

    @PersistenceContext
    private EntityManager entityManager;

    public PaymentsServiceImpl(TransactionsRepository transactionsRepository, CompanyRepository companyRepository, PersonRepository personRepository, ModelMapper modelMapper)
    {
        this.transactionsRepository = transactionsRepository;
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentRequestResponse requestPayment(@Valid PaymentRequest request) throws NotFoundException
    {
        CompanyEntity receiver = companyRepository.findById(request.getRequesterID()).orElseThrow(NotFoundException::new);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setReceiver(receiver);
        transactionEntity.setAmount(request.getAmount());
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionEntity.setNotificationUrl(request.getNotifyEndpoint());

        transactionEntity = transactionsRepository.saveAndFlush(transactionEntity);

        return new PaymentRequestResponse(request, baseURL + transactionEntity.getId().toString());
    }

    @Override
    public TransactionDTO performPayment(String token, PaymentDTO payer) throws NotFoundException, InsufficientFunds, TransactionAlreadyDone
    {
        TransactionEntity transactionEntity = transactionsRepository.findById(UUID.fromString(token)).orElseThrow(NotFoundException::new);
        PersonEntity payerEntity = personRepository.findByCardNumber(payer.getCardNumber()).orElseThrow(NotFoundException::new);

        if (transactionEntity.getPayer() != null)
        {
            throw new TransactionAlreadyDone();
        }

        if (transactionEntity.getAmount().compareTo(payerEntity.getAvailableFunds()) > 0)
        {
            throw new InsufficientFunds();
        }

        payerEntity.setAvailableFunds(payerEntity.getAvailableFunds().subtract(transactionEntity.getAmount()));
        transactionEntity.setPayer(payerEntity);

        TransactionDTO transactionDTO = new TransactionDTO(transactionEntity.getId().toString(),
                transactionEntity.getReceiver().getId(),
                transactionEntity.getAmount(),
                transactionEntity.getTimestamp(),
                transactionEntity.getRedirect(),
                transactionEntity.getNotificationUrl(),
                transactionEntity.getPayer().getId());

        // notify the receiver
        if(transactionDTO.getNotificationURL() != null)
        {
            try
            {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForEntity(transactionDTO.getNotificationURL(), transactionDTO, TransactionDTO.class);
            }
            catch(Exception e){}
        }

        return transactionDTO;
    }
}
