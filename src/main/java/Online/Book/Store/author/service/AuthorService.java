package Online.Book.Store.author.service;

import Online.Book.Store.author.dto.AuthorDTO;
import Online.Book.Store.author.dto.CreateAuthorPayload;
import Online.Book.Store.author.model.Author;

public interface AuthorService {

    AuthorDTO createAuthor(CreateAuthorPayload request);

    Author getAuthorById(Long id);
}
