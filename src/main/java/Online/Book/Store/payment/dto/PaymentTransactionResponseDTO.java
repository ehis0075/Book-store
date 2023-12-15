package Online.Book.Store.payment.dto;

import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentTransactionResponseDTO {

    private String paymentReferenceNumber;

    private BigDecimal amount;

    private Date transactionDate;

    private String customerName;

    private String paymentStatus;

    private String paymentMethod;
}
