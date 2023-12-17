package Online.Book.Store.book.service;

import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.model.Book;

import java.util.List;

public interface BookService {

    BookDTO addBook(CreateUpdateBookRequest request);

    void deductBookStock(Book book, int quantity);

    BookDTO getBookDTO(Book book);

    void validateBook(List<Book> books);

    Book validateBook(Long bookId);

    BookListDTO searchBookList(String query, int pageNumber, int pageSize);
}
