package Online.Book.Store.book.model;

import Online.Book.Store.author.model.Author;
import Online.Book.Store.book.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(unique = true)
    private String isbnCode;

    @OneToOne
    private Author author;

    @Column(name = "publication_year")
    private String publicationYear;

    private BigDecimal price;

    @Column(name = "stock_count")
    private int stockCount;

    public Book(String title, Genre genre, String isbn123, String author, String number, BigDecimal ten) {
    }
}
