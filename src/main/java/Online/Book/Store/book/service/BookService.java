package Online.Book.Store.book.service;

import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.request.SearchBookRequestDTO;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.model.Book;

public interface BookService {

    BookDTO createBook(CreateUpdateBookRequest request);

    void validateBookStockIsNotEmpty(Book book);

    Book findBookById(Long bookId);

    BookListDTO searchBookList(SearchBookRequestDTO requestDTO);
}
