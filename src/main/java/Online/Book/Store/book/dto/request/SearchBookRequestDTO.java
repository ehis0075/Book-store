package Online.Book.Store.book.dto.request;

import Online.Book.Store.book.enums.Genre;
import Online.Book.Store.general.dto.PageableRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SearchBookRequestDTO extends PageableRequestDTO {

    private String title;

    private Genre genre;

    private String isbnCode;

    private String author;

    private String publicationYear;
}
