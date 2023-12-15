package Online.Book.Store.payment.dto;

import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import lombok.Data;

@Data
public class PaymentRequestPayload {

    private PAYMENTMETHOD paymentmethod;

    private String customerName;

}
