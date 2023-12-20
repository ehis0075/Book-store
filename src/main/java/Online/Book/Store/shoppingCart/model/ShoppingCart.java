package Online.Book.Store.shoppingCart.model;

import Online.Book.Store.orderLine.model.OrderLine;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private List<OrderLine> orderLineList;

    public void remove(OrderLine orderLine) {
        orderLineList.remove(orderLine);
    }

    public void add(OrderLine orderLine) {
        orderLineList.add(orderLine);
    }

}
