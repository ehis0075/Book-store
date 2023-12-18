package Online.Book.Store.payment.model;

import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.payment.enums.CHANNEL;
import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import jakarta.persistence.*;
import lombok.Data;

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
    private PAYMENTSTATUS paymentStatus;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private CHANNEL paymentChannel;

}
