package Online.Book.Store.order.model;


import Online.Book.Store.book.model.Book;
import Online.Book.Store.order.dto.OrderLineDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;

    private int quantity;

    private OrderLineDTO getOrderDTO(OrderLine orderLine) {

        OrderLineDTO orderLineDTO = new OrderLineDTO();
        BeanUtils.copyProperties(orderLine, orderLineDTO);

        return orderLineDTO;
    }

}
