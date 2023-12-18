package Online.Book.Store.order.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.order.dto.OrderDTO;
import Online.Book.Store.order.enums.ORDERSTATUS;
import Online.Book.Store.order.model.CustomerOrder;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.repository.OrderRepository;
import Online.Book.Store.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;


    @Override
    public OrderDTO createOrder(CreateOrderPayload request) {
        log.info("Request to create order {}", request);

        Date transactionDate = new Date();

        // get customer
        Customer customer = customerService.findCustomerByEmail(request.getCustomerEmail());

        //get book List
        List<OrderLine> orderLineList = customer.getShoppingCart().getOrderLineList();

        // get total price for all books
        BigDecimal totalPrice = calculateTotalPrice(orderLineList);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setOrderLineList(orderLineList);
        customerOrder.setBillingAddress(request.getBillingAddress());
        customerOrder.setPhoneNumber(request.getPhoneNumber());
        customerOrder.setOrderstatus(ORDERSTATUS.PROCESSING);
        customerOrder.setPaymentmethod(request.getPaymentmethod());
        customerOrder.setTotalCost(totalPrice);
        customerOrder.setOrderDate(transactionDate);

        orderRepository.save(customerOrder);

        return getOrderDTO(customerOrder);
    }

    public int countBooksInOrderLines(List<OrderLine> orderLineList) {
        Set<Book> uniqueBooks = new HashSet<>();

        if (orderLineList != null) {
            for (OrderLine orderLine : orderLineList) {
                if (orderLine != null && orderLine.getBook() != null) {
                    uniqueBooks.add(orderLine.getBook());
                }
            }
        }

        return uniqueBooks.size();
    }

    public BigDecimal calculateTotalPrice(List<OrderLine> orderLineList) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (orderLineList != null) {
            for (OrderLine orderLine : orderLineList) {
                if (orderLine != null && orderLine.getBook() != null) {
                    Book book = orderLine.getBook();
                    totalPrice = totalPrice.add(book.getPrice().multiply(BigDecimal.valueOf(countBooksInOrderLines(orderLineList))));
                }
            }
        }

        return totalPrice;
    }


    private OrderDTO getOrderDTO(CustomerOrder customerOrder) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(customerOrder, orderDTO);

        return orderDTO;
    }
}
