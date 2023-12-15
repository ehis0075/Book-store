package Online.Book.Store.customer.dto;


import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;

    private String name;

    private ShoppingCartDTO shoppingCartDTO;
}
