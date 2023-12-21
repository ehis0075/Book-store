package Online.Book.Store.payment.dto;

import Online.Book.Store.payment.enums.Channel;
import Online.Book.Store.payment.enums.PaymentStatus;
import Online.Book.Store.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class PaymentTransactionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentReferenceNumber;

    private BigDecimal amount;

    @JsonSerialize(using = JsonDateSerializer.class)
    private Date transactionDate;

    private PaymentStatus paymentStatus;

    private Channel paymentChannel;

}