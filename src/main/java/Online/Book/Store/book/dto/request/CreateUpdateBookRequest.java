package Online.Book.Store.book.dto.request;

import Online.Book.Store.book.enums.GENRE;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateUpdateBookRequest {

    private String title;

    private GENRE genre;

    private String author;

    private String publicationYear;

    private BigDecimal amount;

}
