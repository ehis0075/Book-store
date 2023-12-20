package Online.Book.Store.book.dto.request;

import Online.Book.Store.book.enums.Genre;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateUpdateBookRequest {

    private String title;

    private Genre genre;

    private Long authorId;

    private String publicationYear;

    private BigDecimal price;

    private int stockCount;
}
