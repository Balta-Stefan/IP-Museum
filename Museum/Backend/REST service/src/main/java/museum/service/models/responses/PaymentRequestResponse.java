package museum.service.models.responses;

import lombok.Data;
import museum.service.models.requests.PaymentRequest;

@Data
public class PaymentRequestResponse
{
    public static enum PaymentStatus {SUCCESSFUL, UNSUCCESSFUL};

    private final PaymentRequest request;
    private final String redirectURL;
    private final PaymentStatus status;
}