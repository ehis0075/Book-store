package Online.Book.Store.author.service.implementation;

import Online.Book.Store.author.dto.AuthorDTO;
import Online.Book.Store.author.dto.CreateAuthorPayload;
import Online.Book.Store.author.model.Author;
import Online.Book.Store.author.repository.AuthorRepository;
import Online.Book.Store.author.service.AuthorService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDTO createAuthor(CreateAuthorPayload request) {
        log.info("Request to create author {}", request);

        Author author = new Author();
        author.setName(request.getName());

        Author savedAuthor = authorRepository.save(author);

        return getAuthorDTO(savedAuthor);
    }

    @Override
    public Author getAuthorById(Long id) {
        log.info("Getting author by author Id {}", id);

        return authorRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No Author found with the given ID"));
    }

    @Override
    public AuthorDTO getAuthorDTO(Author author) {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(author.getName());

        return authorDTO;
    }
}
