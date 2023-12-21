package Online.Book.Store.book.service.implementaion;

import Online.Book.Store.author.model.Author;
import Online.Book.Store.author.service.AuthorService;
import Online.Book.Store.book.dto.request.CreateUpdateBookRequest;
import Online.Book.Store.book.dto.request.SearchBookRequestDTO;
import Online.Book.Store.book.dto.response.BookDTO;
import Online.Book.Store.book.dto.response.BookListDTO;
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
import org.springframework.beans.BeanUtils;
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

    private final AuthorService authorService;

    @Override
    public BookDTO createBook(CreateUpdateBookRequest request) {
        log.info("Request to create book {}", request);

        Author author = authorService.getAuthorById(request.getAuthorId());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setPrice(request.getPrice());
        book.setAuthor(author);
        book.setStockCount(request.getStockCount());
        book.setIsbnCode(GeneralUtil.generateUniqueISBNNumber());

        Book savedBook = bookRepository.save(book);

        return getBookDTO(savedBook);
    }

    @Override
    public void validateBookStockIsNotEmpty(Book book) {
        log.info("Request to confirm book availability");

        if (book.getStockCount() < 1) {
            log.info("Stock count for book '{}' is less than zero: {}", book.getTitle(), book.getStockCount());
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Book is no longer in Stock");
        }
    }

    @Override
    public Book findBookById(Long bookId) {
        log.info("Request to get a book with title {}", bookId);

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given ID"));
    }

    @Override
    public BookListDTO searchBookList(SearchBookRequestDTO requestDTO) {
        log.info("Search Book List {}", requestDTO);

        Page<Book> bookPage = searchBook(requestDTO);

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

        if (valid(request.getAuthorName())) {
            predicates.add(cb.equal(root.get("authorName"), request.getAuthorName().toLowerCase(Locale.ROOT) + '%'));
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

    public static BookDTO getBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);

        return bookDTO;
    }

}
