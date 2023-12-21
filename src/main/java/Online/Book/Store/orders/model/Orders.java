package Online.Book.Store.orders.model;


import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orders.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Entity
@Data
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderLine> itemList;

    @ManyToOne
    private Customer customer;

    public static OrderDTO getOrderDTO(Orders orders) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orders, orderDTO);

        return orderDTO;
    }

}
