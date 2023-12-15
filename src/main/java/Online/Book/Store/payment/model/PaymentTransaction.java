package Online.Book.Store.payment.model;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    private String customerName;

    @Enumerated(EnumType.STRING)
    private PAYMENTMETHOD paymentMethod;

    @OneToMany
    private List<Book> bookList;
}
