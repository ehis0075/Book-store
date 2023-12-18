package Online.Book.Store.shoppingCart.dto.request;

import lombok.Data;


@Data
public class CreatShoppingCartDTO {

    private String bookId;

    private String customerEmail;
}
