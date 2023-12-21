
package Online.Book.Store.author.service.implementation;

import Online.Book.Store.author.dto.AuthorDTO;
import Online.Book.Store.author.dto.CreateAuthorPayload;
import Online.Book.Store.author.model.Author;
import Online.Book.Store.author.repository.AuthorRepository;
import Online.Book.Store.author.service.AuthorService;
import Online.Book.Store.exception.GeneralException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    void createAuthor() {
        CreateAuthorPayload request = new CreateAuthorPayload();
        request.setName("Test Author");

        Author author = new Author();
        author.setName("Test Author");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.createAuthor(request);

        assertEquals(request.getName(), result.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void getAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("Test Author");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(authorId);

        assertEquals(author.getId(), result.getId());
        assertEquals(author.getName(), result.getName());
    }

    @Test
    void getAuthorById_NotFound() {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    void getAuthorDTO() {
        Author author = new Author();
        author.setName("Test Author");

        AuthorDTO result = authorService.getAuthorDTO(author);

        assertEquals(author.getName(), result.getName());
    }
}