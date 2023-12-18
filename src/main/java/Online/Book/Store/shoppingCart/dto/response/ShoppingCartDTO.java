package Online.Book.Store.shoppingCart.dto.response;

import Online.Book.Store.orderLine.dto.OrderLineDTO;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartDTO {

    private List<OrderLineDTO> orderLineDTOS;
}
