package Online.Book.Store.customer.model;

import Online.Book.Store.shoppingCart.model.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private ShoppingCart shoppingCart;

    public Customer(String s, ShoppingCart shoppingCart) {
    }
}
