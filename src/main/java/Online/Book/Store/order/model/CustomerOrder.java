package Online.Book.Store.order.model;

import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.order.enums.ORDERSTATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billingAddress;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany
    private List<OrderLine> orderLineList;

    private BigDecimal totalCost; //

    @Enumerated(EnumType.STRING)
    private PAYMENTMETHOD paymentmethod; //

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private ORDERSTATUS orderstatus;

}