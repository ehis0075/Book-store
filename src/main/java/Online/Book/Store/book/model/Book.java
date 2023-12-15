package Online.Book.Store.book.model;

import Online.Book.Store.book.enums.GENRE;
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

    private String title;

    @Enumerated(EnumType.STRING)
    private GENRE genre;

    private String isbnCode;

    private String author;

    private String publicationYear;

    private BigDecimal amount;


    public Book(String s, GENRE genre, String number, String s1, String number1, BigDecimal bigDecimal) {
    }
}
