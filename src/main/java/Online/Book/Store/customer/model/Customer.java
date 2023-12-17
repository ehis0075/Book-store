package Online.Book.Store.customer.model;

import Online.Book.Store.order.model.Order;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne
    private ShoppingCart shoppingCart;

}
