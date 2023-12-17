package Online.Book.Store.customer.dto;


import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private ShoppingCartDTO shoppingCartDTO;
}
