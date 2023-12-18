package Online.Book.Store.order.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.dto.OrderLineDTO;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.repository.OrderLineRepository;
import Online.Book.Store.order.service.OrderLineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public OrderLine createOrderLine(CreateOrderLinePayload request) {
        log.info("Request to create order line {}", request);

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given ID"));

        OrderLine orderLine = new OrderLine();
        orderLine.setBook(book);

        orderLine = orderLineRepository.save(orderLine);

        return orderLine;
    }

    @Override
    public OrderLine findByBookId(Long bookId) {
        log.info("Request to get order line with {}", bookId);

        return orderLineRepository.findById(bookId)
                .orElseThrow(()  -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No book found with the given ID"));
    }


    public void addOrderLine(Book book, int quantity) {

        OrderLine orderLine = new OrderLine();
        orderLine.setBook(book);

    }

    public BigDecimal calculateTotalPrice(List<Book> bookList) {
        return bookList.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    private int getQuantityForBook(Book book, List<OrderLine> orderLineList) {
//        // Find the corresponding OrderLine for this book in the order
//        for (OrderLine orderLine : orderLineList) {
//            if (orderLine.getBook().equals(book)) {
//                return orderLine.getQuantity(); // Return quantity from OrderLine
//            }
//        }
//        // If no OrderLine found for this book, throw an exception or log an error
//        throw new RuntimeException("OrderLine not found for book: " + book.getTitle());
//    }

    private OrderLineDTO getOrderDTO(OrderLine orderLine) {

        OrderLineDTO orderLineDTO = new OrderLineDTO();
        BeanUtils.copyProperties(orderLine, orderLineDTO);

        return orderLineDTO;
    }
}
