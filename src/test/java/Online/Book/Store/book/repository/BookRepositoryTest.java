package Online.Book.Store.book.repository;

import Online.Book.Store.book.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown(){
        bookRepository.deleteAll();
    }

    @Test
    void existsByTitle() {

        //given
        String bookTitle = "Nigeria at 50";
        Book book = new Book();
        book.setTitle(bookTitle);
        bookRepository.save(book);

        //when
        boolean existByTitle = bookRepository.existsByTitle(bookTitle);
        //Assert
        assertTrue(existByTitle);
    }

    @Test
    void findByTitle() {

        String bookTitle = "Nigeria at 50";
        Book book = new Book();
        book.setTitle(bookTitle);
        bookRepository.save(book);

        //when
        Book retrievedBook = bookRepository.findByTitle(bookTitle);

        //assert
        assertNotNull(retrievedBook);
        assertEquals(bookTitle, retrievedBook.getTitle());
    }


}