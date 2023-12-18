package Online.Book.Store.orderLine.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.repository.OrderLineRepository;
import Online.Book.Store.orderLine.service.OrderLineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final BookRepository bookRepository;

    @Override
    public OrderLine createOrderLine(Long bookId) {
        log.info("Request to create order line for book with ID{}", bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given book ID"));

        OrderLine orderLine = new OrderLine();
        orderLine.setBook(book);

        orderLine = saveOrderLine(orderLine);

        return orderLine;
    }

    @Override
    public OrderLine findOrderLineByBookId(Long bookId) {
        log.info("Request to get order line with {}", bookId);

        return orderLineRepository.findById(bookId)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given ID"));
    }

    public BigDecimal calculateTotalPrice(List<Book> bookList) {
        return bookList.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderLine saveOrderLine(OrderLine orderLine) {

        return orderLineRepository.save(orderLine);
    }
}
