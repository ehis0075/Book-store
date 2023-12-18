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
import Online.Book.Store.order.model.OrderLine;
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

import java.math.BigDecimal;
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
        book.setPrice(request.getAmount());

        Book savedBook = bookRepository.save(book);

        return getBookDTO(savedBook);
    }

    @Override
    public void decreaseBookStock(Book book, int quantity) {
        log.info("decreasing book stock count in database");

        book.setStockCount(book.getStockCount() - quantity);

        // Save updated book information
        bookRepository.save(book);
    }

    @Override
    public void increaseBookStock(Book book, int quantity) {
        log.info("increasing book stock count in database");

        book.setStockCount(book.getStockCount() + quantity);

        // Save updated book information
        bookRepository.save(book);
    }

    @Override
    public void saveBook(Book book) {

        // Save updated book information
        bookRepository.save(book);
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
        bookDTO.setAmount(book.getPrice());

        return bookDTO;
    }

    private int getQuantityForBook(Book book, List<OrderLine> orderLineList) {
        // Find the corresponding OrderLine for this book in the order
        for (OrderLine orderLine : orderLineList) {
            if (orderLine.getBook().equals(book)) {
                return orderLine.getQuantity(); // Return quantity from OrderLine
            }
        }
        // If no OrderLine found for this book, throw an exception or log an error
        throw new RuntimeException("OrderLine not found for book: " + book.getTitle());
    }

    public BigDecimal calculateTotalPrice(List<Book> bookList) {
        return bookList.stream()
                .map(Book::getPrice) // Assuming you have a getPrice() method in your Book class
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void validateBook(List<OrderLine> books) {

        List<Long> bookIds = books.stream()
                .map(OrderLine::getId)
                .collect(Collectors.toList());

        List<Book> bookList = bookRepository.findAllById(bookIds);

        if (bookList.isEmpty()) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No books found with the given IDs");
        }
    }

    @Override
    public Book validateBookById(Long bookId) {

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given ID"));
    }

    @Override
    public Book findBookByTitle(String bookTitle) {
        log.info("Request to get a book with title {}", bookTitle);

        Book book = bookRepository.findByTitle(bookTitle);

        if (Objects.isNull(book)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given title");
        }
        return book;
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
