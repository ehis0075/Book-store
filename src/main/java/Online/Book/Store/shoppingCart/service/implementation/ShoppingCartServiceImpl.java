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

    private final CustomerService customerService;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public void addToCart(CreatShoppingCartDTO request) {
        log.info("Request to add book to shopping cart {}", request);

        Book book = bookService.findBookById(request.getBookId());

        //get customer
        Customer customer = customerService.findCustomerByEmail(request.getCustomerEmail());

        // validate that Book stock is available
        bookService.validateBookStockIsNotEmpty(book);

        // create orderLine
        OrderLine orderLine = orderLineService.createOrderLine(book.getId());

        // Retrieve the shopping cart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        log.info("adding book to shopping cart");
        shoppingCart.getOrderLineList().add(orderLine);

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

        List<OrderLineDTO> orderLineDTOS = getOrderLineDTOS(shoppingCart.getOrderLineList());

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
        Customer customer = customerService.findCustomerByEmail(request.getCustomerEmail());

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

            // Save the updated shopping cart
            shoppingCartRepository.save(customer.getShoppingCart());

            log.info("Successfully removed book from shopping cart");

        } else {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Book not found in the shopping cart");
        }
    }

    @Override
    public List<OrderLine> getAllItems(String customerEmail) {
        log.info("Request to get all items in a shopping cart for {}", customerEmail);

        //get customer
        Customer customer = customerService.findCustomerByEmail(customerEmail);

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

        shoppingCart.getOrderLineList().clear();
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
