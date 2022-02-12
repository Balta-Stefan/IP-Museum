package museum.service.models.responses;

import lombok.Data;
import museum.service.models.requests.PaymentRequest;

@Data
public class PaymentRequestResponse
{
    private final PaymentRequest request;
    private final String redirectURL;
}