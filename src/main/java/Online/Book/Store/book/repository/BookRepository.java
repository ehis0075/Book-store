package Online.Book.Store.book.repository;

import Online.Book.Store.book.enums.Genre;
import Online.Book.Store.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitle(String title);

    Book findByTitle(String title);

    @Query("SELECT p FROM Book p WHERE p.title = :title")
    Page<Book> searchByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT p FROM Book p WHERE p.author = :author")
    Page<Book> searchByAuthor(@Param("author") String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.publicationYear = :year")
    Page<Book> searchByPublicationYear(@Param("year") String publicationYear, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.genre = :genre")
    Page<Book> searchByGenre(@Param("genre") Genre genre, Pageable pageable);


}
