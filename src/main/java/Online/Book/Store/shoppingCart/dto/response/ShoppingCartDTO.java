package Online.Book.Store.shoppingCart.dto.response;

import Online.Book.Store.book.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartDTO {

    private List<Book> bookList;
}
