package Online.Book.Store.book.dto.response;

import Online.Book.Store.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class BookListDTO extends PageableResponseDTO {

    @JsonProperty("books")
    private List<BookDTO> bookDTOList;
}