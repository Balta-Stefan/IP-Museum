package museum.service.controllers;

import museum.service.models.DTOs.BankNotificationDTO;
import museum.service.services.BankNotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController
{
    private final BankNotificationService bankNotificationService;

    public TransactionsController(BankNotificationService bankNotificationService)
    {
        this.bankNotificationService = bankNotificationService;
    }

    @PostMapping
    public void reportTransactionStatus(@RequestBody @Valid BankNotificationDTO notificationDTO)
    {
        bankNotificationService.handlePayment(notificationDTO);
    }
}
