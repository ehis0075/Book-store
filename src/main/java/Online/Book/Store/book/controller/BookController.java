package Online.Book.Store.book.controller;


import Online.Book.Store.book.dto.request.SearchBookRequestDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    private final GeneralService generalService;

    @GetMapping("/search")
    public Response searchBook(@RequestBody SearchBookRequestDTO requestDTO) {
        BookListDTO data = bookService.searchBookList(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
