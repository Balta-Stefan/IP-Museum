package museum.service.models.responses;

import lombok.Data;
import museum.service.models.requests.PaymentRequest;

@Data
public class PaymentRequestResponse
{
    public enum PaymentStatus {SUCCESSFUL, UNSUCCESSFUL};

    private PaymentRequest request;
    private String redirectURL;
    private PaymentStatus status;
}