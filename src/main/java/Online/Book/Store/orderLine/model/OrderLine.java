package Online.Book.Store.orderLine.model;


import Online.Book.Store.book.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class OrderLine {   // use equal hash code here : book id will be the equal and hash code

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    // qty : min of 1
    private int count;
}
