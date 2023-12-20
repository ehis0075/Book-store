package Online.Book.Store.book.dto.response;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.enums.Genre;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
public class BookDTO {

    private Long id;

    private String title;

    private Genre genre;

    private String isbnCode;

    private String author;

    private String publicationYear;

    private BigDecimal amount;


    public static BookDTO getBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);

        return bookDTO;
    }
}
