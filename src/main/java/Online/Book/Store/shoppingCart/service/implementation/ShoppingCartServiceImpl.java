package Online.Book.Store.shoppingCart.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.orderLine.dto.OrderLineDTO;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.service.OrderLineService;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.repository.ShoppingCartRepository;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final OrderLineService orderLineService;

    private final BookService bookService;

    private final CustomerService customerService;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public void addToCart(CreatShoppingCartDTO request) {
        log.info("Request to add book to shopping cart {}", request);

        Book book = bookService.findBookById(request.getBookId());

        //get customer
        Customer customer = customerService.findCustomerById(request.getCustomerId());

        // validate that Book stock is available
        bookService.validateBookStockIsNotEmpty(book);

        // Retrieve the shopping cart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        // get order line list
        Set<OrderLine> orderLineList = shoppingCart.getOrderLineList();

        log.info("adding book to shopping cart");
        addOrUpdateBook(book, orderLineList);

        shoppingCartRepository.save(shoppingCart);
        log.info("successfully added book to shopping cart");
    }

    @Override
    public ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart) {

        for (OrderLine orderLine : shoppingCart.getOrderLineList()) {
            OrderLineDTO orderLineDTO = new OrderLineDTO();
            orderLineDTO.setId(orderLine.getId());
            orderLineDTO.setBook(orderLine.getBook());
        }

        Set<OrderLineDTO> orderLineDTOS = getOrderLineDTOS(shoppingCart.getOrderLineList());

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setOrderLineDTOS(orderLineDTOS);

        return shoppingCartDTO;
    }

    @Override
    public void removeFromCart(CreatShoppingCartDTO request) {
        log.info("Request to remove book from shopping cart {}", request);

        // Retrieve the book
        Book book = bookService.findBookById(request.getBookId());

        // Retrieve the customer
        Customer customer = customerService.findCustomerById(request.getCustomerId());

        // Retrieve the shopping cart
        Set<OrderLine> orderLineList = customer.getShoppingCart().getOrderLineList();

        // Use the removeBook method
        removeBook(book, orderLineList);

        // Save the updated shopping cart
        shoppingCartRepository.save(customer.getShoppingCart());
        log.info("Successfully removed book from shopping cart");
    }

    @Override
    public Set<OrderLine> getAllItems(Long customerId) {
        log.info("Request to get all items in a shopping cart for {}", customerId);

        //get customer
        Customer customer = customerService.findCustomerById(customerId);

        // Retrieve the book list from the shopping cart
        return customer.getShoppingCart().getOrderLineList();
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(Long shoppingCartId) {
        log.info("Request to clear shopping cart");

        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Shopping cart Not found"));

        Set<OrderLine> orderLineList = shoppingCart.getOrderLineList();

        // Clear the orderLineList from the shopping cart
        orderLineList.clear();

        //update the ShoppingCart entity in the database
        shoppingCartRepository.save(shoppingCart);
    }


    // Add this method to handle book addition or count update
    public void addOrUpdateBook(Book book, Set<OrderLine> orderLineList) {

        // Check if the book is already in the orderLineList
        Optional<OrderLine> existingOrderLine = orderLineList.stream()
                .filter(orderLine -> orderLine.getBook().equals(book))
                .findFirst();

        if (existingOrderLine.isPresent()) {
            // If the book exists, increase the count by 1
            existingOrderLine.get().setCount(existingOrderLine.get().getCount() + 1);

            orderLineService.saveItem(existingOrderLine.get());
        } else {

            // If the book does not exist, create a new OrderLine
            orderLineService.createOrderLine(book, orderLineList);
        }
    }

    // Method to remove a book and update the count
    public void removeBook(Book book, Set<OrderLine> orderLineList) {
        // Check if the book is in the orderLineList
        Optional<OrderLine> existingOrderLine = orderLineList.stream()
                .filter(orderLine -> orderLine.getBook().equals(book))
                .findFirst();

        if (existingOrderLine.isPresent()) {
            // If the book exists, decrease the count by 1
            OrderLine orderLine = existingOrderLine.get();
            int newCount = orderLine.getCount() - 1;

            if (newCount > 0) {
                orderLine.setCount(newCount);
            } else {
                // If the count becomes zero, remove the OrderLine from the list
                orderLineList.remove(orderLine);
            }
        }
        orderLineService.saveItem(existingOrderLine.get());
    }

    private Set<OrderLineDTO> getOrderLineDTOS(Set<OrderLine> orderLineList) {
        return orderLineList.stream()
                .map(this::convertToOrderLineDTO)
                .collect(Collectors.toSet());
    }

    private OrderLineDTO convertToOrderLineDTO(OrderLine orderLine) {

        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setId(orderLine.getId());
        orderLineDTO.setBook(orderLine.getBook());
        return orderLineDTO;
    }

}
