package Online.Book.Store.payment.dto;

import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestPayload {

    private PAYMENTMETHOD paymentmethod;

    private String customerEmail;

    private BigDecimal amount;

}
