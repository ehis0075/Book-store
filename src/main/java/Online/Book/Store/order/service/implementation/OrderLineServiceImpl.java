package Online.Book.Store.order.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.order.dto.OrderDTO;
import Online.Book.Store.order.dto.OrderLineDTO;
import Online.Book.Store.order.enums.ORDERSTATUS;
import Online.Book.Store.order.model.Order;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.repository.OrderLineRepository;
import Online.Book.Store.order.repository.OrderRepository;
import Online.Book.Store.order.service.OrderLineService;
import Online.Book.Store.order.service.OrderService;
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

    private final BookService bookService;

    @Override
    public OrderLineDTO createOrderLine(CreateOrderLinePayload request) {
        log.info("Request to create order line{}", request);

        Book book = bookService.validateBook(request.getBookId());

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(request.getQty());
        orderLine.setBook(book);

        orderLineRepository.save(orderLine);
    }

    public void addOrderLine(Book book, int quantity) {

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(quantity);
        orderLine.setBook(book);

    }

    public BigDecimal calculateTotalPrice(List<Book> bookList) {
        return bookList.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    private OrderDTO getOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);

        return orderDTO;
    }
}
