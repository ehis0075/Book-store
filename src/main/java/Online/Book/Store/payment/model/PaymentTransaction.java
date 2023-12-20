package Online.Book.Store.payment.model;

import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.payment.dto.PaymentTransactionDTO;
import Online.Book.Store.payment.enums.Channel;
import Online.Book.Store.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String paymentReferenceNumber;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Channel paymentChannel;

    public static PaymentTransactionDTO getPaymentTransactionDTO(PaymentTransaction paymentTransaction) {
        PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();
        BeanUtils.copyProperties(paymentTransaction, paymentTransactionDTO);

        return paymentTransactionDTO;
    }

}
