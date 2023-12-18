package Online.Book.Store.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentTransactionResponseDTO {
    private String paymentReferenceNumber;
    private BigDecimal amount;
    private String paymentGatewayUrl;
}
