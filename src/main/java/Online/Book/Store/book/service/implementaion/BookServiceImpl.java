package Online.Book.Store.book.service.implementaion;

import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.request.SearchBookRequestDTO;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
import Online.Book.Store.book.enums.GENRE;
import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.util.GeneralUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    @PersistenceContext
    private EntityManager em;

    private final BookRepository bookRepository;

    @Override
    public BookDTO addBook(CreateUpdateBookRequest request) {
        log.info("Request to add book with payload {}", request);

        //validate book
        boolean isExist = bookRepository.existsByTitle(request.getTitle());

        if (isExist) {
            throw new GeneralException(ResponseCodeAndMessage.ALREADY_EXIST_86.responseMessage, "Book with title already exist");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setAuthor(request.getAuthor());
        book.setIsbnCode(GeneralUtil.generateISBN());
        book.setPublicationYear(request.getPublicationYear());
        book.setAmount(request.getAmount());

        Book savedBook = bookRepository.save(book);

        return getBookDTO(savedBook);
    }


    @Override
    public BookDTO getBookDTO(Book book) {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookDTO.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbnCode(book.getIsbnCode());
        bookDTO.setPublicationYear(book.getPublicationYear());
        bookDTO.setAmount(book.getAmount());

        return bookDTO;
    }

    @Override
    public BookListDTO searchBookList(String query, int pageNumber, int pageSize) {

        String upperCase = query.toUpperCase();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Book> bookPage;

        switch (upperCase) {
            case "TITLE":
                bookPage = bookRepository.searchByTitle(query, pageable);
                break;
            case "PUBLICATIONYEAR":
                bookPage = bookRepository.searchByPublicationYear(query, pageable);
                break;
            case "AUTHOR":
                bookPage = bookRepository.searchByAuthor(query, pageable);
                break;
            case "GENRE":
                bookPage = bookRepository.searchByGenre(GENRE.valueOf(query), pageable);
                break;
            default:
                throw new IllegalArgumentException("Invalid query type: " + query);
        }

        return getBookListDTO(bookPage);
    }

    private BookListDTO getBookListDTO(Page<Book> bookPage) {
        BookListDTO listDTO = new BookListDTO();

        List<Book> bookList = bookPage.getContent();
        if (bookPage.getContent().size() > 0) {
            listDTO.setHasNextRecord(bookPage.hasNext());
            listDTO.setTotalCount((int) bookPage.getTotalElements());
        }

        List<BookDTO> bookDTOS = convertToBookDTOList(bookList);
        listDTO.setBookDTOList(bookDTOS);
        return listDTO;
    }

    private List<BookDTO> convertToBookDTOList(List<Book> bookList) {
        log.info("Converting Book List to Book DTO List");

        return bookList.stream().map(BookDTO::getBookDTO).collect(Collectors.toList());
    }

    public Page<Book> searchBook(SearchBookRequestDTO request) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> root = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (valid(request.getTitle())) {
            predicates.add(cb.like(cb.lower(root.get("title")), '%' + request.getTitle().toLowerCase(Locale.ROOT) + '%'));
        }

        if (valid(request.getPublicationYear())) {
            predicates.add(cb.like(cb.lower(root.get("publicationYear")), '%' + request.getPublicationYear().toLowerCase(Locale.ROOT) + '%'));
        }

        if (valid(request.getAuthor())) {
            predicates.add(cb.equal(root.get("author"), request.getAuthor().toLowerCase(Locale.ROOT) + '%'));
        }

        if (Objects.nonNull(request.getGenre())) {
            predicates.add(cb.equal(root.get("genre"), request.getGenre()));
        }

        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<?> query = em.createQuery(cq);

        return (Page<Book>) getPage(request.getPage(), request.getSize(), query);
    }

    private boolean valid(String value) {
        return !GeneralUtil.stringIsNullOrEmpty(value);
    }

    private PageImpl<?> getPage(int page, int size, TypedQuery<?> query) {
        Pageable paged;
        int totalRows;

        paged = PageRequest.of(page, size);
        totalRows = query.getResultList().size();

        query.setFirstResult(paged.getPageNumber() * paged.getPageSize());
        query.setMaxResults(paged.getPageSize());

        return new PageImpl<>(query.getResultList(), paged, totalRows);
    }

}
