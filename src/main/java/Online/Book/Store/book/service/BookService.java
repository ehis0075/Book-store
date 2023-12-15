package Online.Book.Store.book.service;

import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.model.Book;

public interface BookService {

    BookDTO addBook(CreateUpdateBookRequest request);

    BookDTO getBookDTO(Book book);

    BookListDTO searchBookList(String query, int pageNumber, int pageSize);
}
