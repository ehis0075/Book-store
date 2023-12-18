package Online.Book.Store.shoppingCart.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.dto.OrderLineDTO;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.service.OrderLineService;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.repository.ShoppingCartRepository;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final OrderLineService orderLineService;

    private final BookService bookService;

    private final CustomerRepository customerRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartDTO addToCart(CreatShoppingCartDTO request) {
        log.info("Request to add book to shopping cart {}", request);

        Book book = bookService.findBookByTitle(request.getBookId());

        //get customer
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail());

        if (Objects.isNull(customer)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Customer does not exist");
        }

        CreateOrderLinePayload createOrderLinePayload = new CreateOrderLinePayload();
        createOrderLinePayload.setBookId(book.getId());

        // create orderLine
        OrderLine orderLine = orderLineService.createOrderLine(createOrderLinePayload);

        // validate that Book stock is available
        boolean isAvailable = bookService.validateBookStockIsNotEmpty(orderLine);

        // Retrieve the shopping cart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        log.info("adding book to shopping cart");
        shoppingCart.getOrderLineList().add(orderLine);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info("successfully added book to shopping cart");

        // deduct from book stock
        bookService.decreaseBookStock(book);

        return getShoppingCartDTO(savedShoppingCart);
    }

    @Override
    public ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart) {

        for (OrderLine orderLine : shoppingCart.getOrderLineList()) {
            OrderLineDTO orderLineDTO = new OrderLineDTO();
            orderLineDTO.setId(orderLine.getId());
            orderLineDTO.setBook(orderLine.getBook());
        }

        List<OrderLineDTO> orderLineDTOS = getOrderLineDTOS(shoppingCart.getOrderLineList());

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setOrderLineDTOS(orderLineDTOS);

        return shoppingCartDTO;
    }

//    @Override
//    public ShoppingCartDTO removeFromCart(CreatShoppingCartDTO request) {
//        log.info("Request to remove book from shopping cart {}", request);
//
//        Book book = bookService.findBookByTitle(request.getBookId());
//
//        // Retrieve the customer
//        Customer customer = customerRepository.findByEmail(request.getCustomerEmail());
//
//        if (Objects.isNull(customer)) {
//            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Customer does not exist");
//        }
//
//        // Retrieve the shopping cart
//        ShoppingCart shoppingCart = customer.getShoppingCart();
//
//        // get orderLine
//        OrderLine orderLine = orderLineService.findByBookId(book.getId());
//
//        List<OrderLine> orderLineList = shoppingCart.getOrderLineList();
//
//        log.info("removing book from shopping cart");
//        orderLineList.remove(orderLine);
//
////        // reverse book stock
////        bookService.reverseBookStock(book, orderLine.getQuantity());
//
//        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
//        log.info("successfully removed book from shopping cart");
//
//        return getShoppingCartDTO(savedShoppingCart);
//    }

    @Override
    public ShoppingCartDTO removeFromCart(CreatShoppingCartDTO request) {
        log.info("Request to remove book from shopping cart {}", request);

        // Retrieve the book
        Book book = bookService.findBookByTitle(request.getBookId());

        // Retrieve the customer
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail());

        if (Objects.isNull(customer)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Customer does not exist");
        }

        // Find the corresponding OrderLine in the shopping cart
        Optional<OrderLine> orderLineOptional = customer.getShoppingCart().getOrderLineList().stream()
                .filter(orderLine -> orderLine.getBook().equals(book))
                .findFirst();

        if (orderLineOptional.isPresent()) {
            OrderLine orderLineToRemove = orderLineOptional.get();

            // Remove the OrderLine from the shopping cart
            customer.getShoppingCart().remove(orderLineToRemove);

            // Update book stock (assuming you have a method to increase the stock)
            bookService.increaseBookStock(book);

            // Save the updated shopping cart
            ShoppingCart savedShoppingCart = shoppingCartRepository.save(customer.getShoppingCart());

            log.info("Successfully removed book from shopping cart");

            return getShoppingCartDTO(savedShoppingCart);
        } else {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Book not found in the shopping cart");
        }
    }


    @Override
    public List<OrderLine> getAllItems(String customerEmail) {
        log.info("Request to get all items in a shopping cart for {}", customerEmail);

        //get customer
        Customer customer = customerRepository.findByEmail(customerEmail);

        // Retrieve the book list from the shopping cart

        return customer.getShoppingCart().getOrderLineList();
    }

    public void remove(List<OrderLine> orderLineList, OrderLine orderLine) {
        orderLineList.remove(orderLine);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(Long shoppingCartId) {
        log.info("Request to clear shopping cart");

        shoppingCartRepository.deleteById(shoppingCartId);
    }

    private List<OrderLineDTO> getOrderLineDTOS(List<OrderLine> orderLineList) {
        return orderLineList.stream()
                .map(this::convertToOrderLineDTO)
                .collect(Collectors.toList());
    }

    private OrderLineDTO convertToOrderLineDTO(OrderLine orderLine) {

        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setId(orderLine.getId());
        orderLineDTO.setBook(orderLine.getBook());
        return orderLineDTO;
    }

}
