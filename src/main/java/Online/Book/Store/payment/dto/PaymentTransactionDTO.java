package Online.Book.Store.payment.dto;

import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.payment.enums.Channel;
import Online.Book.Store.payment.enums.PaymentStatus;
import Online.Book.Store.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
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

    private CustomerDTO customerDTO;

    private Channel paymentChannel;

}