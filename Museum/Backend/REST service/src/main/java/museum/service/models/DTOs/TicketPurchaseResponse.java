package museum.service.models.DTOs;

import lombok.Data;

@Data
public class TicketPurchaseResponse
{
    private final String redirectURL;
    private final String ticketID;
}
