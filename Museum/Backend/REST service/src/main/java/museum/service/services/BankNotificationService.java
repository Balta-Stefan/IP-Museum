package museum.service.services;

import museum.service.models.DTOs.BankNotificationDTO;

public interface BankNotificationService
{
    void handlePayment(BankNotificationDTO notificationDTO);
}
