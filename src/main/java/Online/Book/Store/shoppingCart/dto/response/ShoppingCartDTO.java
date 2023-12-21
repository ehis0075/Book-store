package Online.Book.Store.shoppingCart.dto.response;

import Online.Book.Store.orderLine.dto.OrderLineDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ShoppingCartDTO {

    private Set<OrderLineDTO> orderLineDTOS;
}
