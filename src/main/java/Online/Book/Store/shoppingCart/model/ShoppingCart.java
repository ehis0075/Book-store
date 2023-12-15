package Online.Book.Store.shoppingCart.model;

import Online.Book.Store.book.model.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Book> bookList;

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

}
