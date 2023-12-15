package Online.Book.Store.shoppingCart.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.repository.ShoppingCartRepository;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final GeneralService generalService;

    private final BookRepository bookRepository;

    private final CustomerRepository customerRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartDTO addToCart(CreatShoppingCartDTO request) {
        log.info("Request to add book to shopping cart {}", request);

        Book book = bookRepository.findByTitle(request.getBookTitle());

        if (Objects.isNull(book)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Book does not exist");
        }

        //get customer
        Customer customer = customerRepository.findByName(request.getCustomerName());

        if (Objects.isNull(customer)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Customer does not exist");
        }

        // Retrieve the shopping cart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        log.info("adding book to shopping cart");
        shoppingCart.addBook(book);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info("successfully added book to shopping cart");

        return getShoppingCartDTO(savedShoppingCart);
    }

    @Override
    public ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart) {

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setBookList(shoppingCart.getBookList());

        return shoppingCartDTO;
    }

    @Override
    public ShoppingCartDTO removeFromCart(CreatShoppingCartDTO request) {
        log.info("Request to remove book from shopping cart {}", request);

        Book book = bookRepository.findByTitle(request.getBookTitle());

        if (Objects.isNull(book)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Book does not exist");
        }

        //get customer
        Customer customer = customerRepository.findByName(request.getCustomerName());

        // Retrieve the shopping cart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        log.info("removing book from shopping cart");
        shoppingCart.removeBook(book);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info("successfully removed book from shopping cart");

        return getShoppingCartDTO(savedShoppingCart);
    }

    @Override
    public List<Book> getAllItems(String customerName) {
        log.info("Request to get all items in a shopping cart for {}", customerName);

        //get customer
        Customer customer = customerRepository.findByName(customerName);

        // Retrieve the book list from the shopping cart
        List<Book> bookList = customer.getShoppingCart().getBookList();

        return bookList;
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
}
