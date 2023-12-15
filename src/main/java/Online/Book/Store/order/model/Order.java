package Online.Book.Store.order.model;

import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import Online.Book.Store.customer.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billingAddress;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PAYMENTMETHOD paymentmethod;

}