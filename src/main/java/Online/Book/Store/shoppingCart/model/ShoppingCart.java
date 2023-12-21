package Online.Book.Store.shoppingCart.model;

import Online.Book.Store.orderLine.model.OrderLine;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "shopping_cart_order_line_list",
            joinColumns = @JoinColumn(
                    name = "shopping_cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "order_line_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<OrderLine> orderLineList;

    public void add(OrderLine orderLine) {
        orderLineList.add(orderLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return id != null && Objects.equals(id, shoppingCart.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
