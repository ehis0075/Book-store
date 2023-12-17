package Online.Book.Store.order.dto;

import Online.Book.Store.book.model.Book;
import lombok.Data;


@Data
public class OrderLineDTO {

    private Long id;

    private int quantity;

    private Book book;
}
