package Online.Book.Store.order.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.order.dto.OrderDTO;
import Online.Book.Store.order.enums.ORDERSTATUS;
import Online.Book.Store.order.model.Order;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.repository.OrderRepository;
import Online.Book.Store.order.service.OrderLineService;
import Online.Book.Store.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final OrderLineService orderLineService;


    @Override
    public OrderDTO createOrder(CreateOrderPayload request) {
        log.info("Request to create order {}", request);

        // get customer
        Customer customer = customerRepository.findByName(request.getCustomerName());

        //get book List
        List<Book> bookList = customer.getShoppingCart().getBookList();

        // get total price for all books
        BigDecimal totalPrice = calculateTotalPrice(bookList);

        int bookQty;
        //get qty for book
        for (Book book : bookList) {
            bookQty = getQuantityForBook(book, );
        }

        //
        List<OrderLine> orderLineList = null;


        //create order line
        for(Book book: bookList){
            OrderLine orderLine = new OrderLine();
            orderLine.setQuantity(request.getQty());
            orderLine.setBook(book);

            orderLineList = new ArrayList<>();
            orderLineList.add(orderLine);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderLine(orderLineList);
        order.setBillingAddress(request.getBillingAddress());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setOrderstatus(ORDERSTATUS.PROCESSING);
        order.setPaymentmethod(request.getPaymentmethod());
        order.setTotalCost(totalPrice);

        orderRepository.save(order);

        return getOrderDTO(order);
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

    private OrderDTO getOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);

        return orderDTO;
    }
}
