package Online.Book.Store.book.service;

import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.model.Book;
import Online.Book.Store.orderLine.model.OrderLine;

import java.util.List;

public interface BookService {

    BookDTO addBook(CreateUpdateBookRequest request);

    void decreaseBookStock(Book book);

    void increaseBookStock(Book book);

    void saveBook(Book book);

    BookDTO getBookDTO(Book book);

    void validateBook(List<OrderLine> books);

    void validateBookStockIsNotEmpty(Book book);

    void validateBookStockExist(List<OrderLine> orderLineList);

    Book validateBookById(Long bookId);

    Book findBookById(Long bookId);

    BookListDTO searchBookList(String query, int pageNumber, int pageSize);
}
