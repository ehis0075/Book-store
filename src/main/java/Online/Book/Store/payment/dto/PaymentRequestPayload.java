package Online.Book.Store.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestPayload {

    private String customerEmail;

    private BigDecimal amount;

}
