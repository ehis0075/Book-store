package Online.Book.Store.shoppingCart.dto.request;

import lombok.Data;


@Data
public class CreatShoppingCartDTO {

    private Long bookId;

    private String customerEmail;
}
