package Online.Book.Store.book.controller;


import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.response.BookDTO;
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

    @PostMapping("/add")
    public Response addBook(@RequestBody CreateUpdateBookRequest requestDTO) {

        BookDTO data = bookService.addBook(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("/search")
    public Response searchBook(@RequestParam("query") String query, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "20") int pageSize) {
        BookListDTO data = bookService.searchBookList(query, pageNumber, pageSize);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
