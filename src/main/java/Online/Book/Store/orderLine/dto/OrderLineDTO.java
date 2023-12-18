package Online.Book.Store.orderLine.dto;

import Online.Book.Store.book.model.Book;
import lombok.Data;


@Data
public class OrderLineDTO {

    private Long id;

    private Book book;
}
