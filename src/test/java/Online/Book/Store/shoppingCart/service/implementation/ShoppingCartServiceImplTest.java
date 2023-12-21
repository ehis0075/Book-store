package Online.Book.Store.shoppingCart.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.service.OrderLineService;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.repository.ShoppingCartRepository;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ShoppingCartServiceImplTest {

    @Mock
    private BookService bookService;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderLineService orderLineService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    public void testAddToCart() {

        // Mock data
        CreatShoppingCartDTO request = new CreatShoppingCartDTO();
        request.setBookId(1L);
        request.setCustomerId(1L);

        Book book = new Book();
        book.setId(1L);

        Customer customer = new Customer();
        customer.setId(1L);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setOrderLineList(new HashSet<>());

        when(bookService.findBookById(1L)).thenReturn(book);
        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(customer.getShoppingCart()).thenReturn(shoppingCart);

        // Test the method
        shoppingCartService.addToCart(request);

        // Verify interactions
        verify(bookService).findBookById(1L);
        verify(customerService).findCustomerById(1L);
        verify(bookService).validateBookStockIsNotEmpty(book);
        verify(shoppingCartRepository).save(shoppingCart);
    }

    @Test
    public void testRemoveFromCart() {
        // Mock data
        CreatShoppingCartDTO request = new CreatShoppingCartDTO();
        request.setBookId(1L);
        request.setCustomerId(1L);

        Book book = new Book();
        book.setId(1L);

        Customer customer = new Customer();
        customer.setId(1L);

        OrderLine orderLine = new OrderLine();
        orderLine.setId(1L);
        orderLine.setBook(book);
        orderLine.setQuantity(1);

        Set<OrderLine> orderLineList = new HashSet<>();
        orderLineList.add(orderLine);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setOrderLineList(orderLineList);

        when(bookService.findBookById(1L)).thenReturn(book);
        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(customer.getShoppingCart()).thenReturn(shoppingCart);

        // Test the method
        shoppingCartService.removeFromCart(request);

        // Verify interactions
        verify(bookService).findBookById(1L);
        verify(customerService).findCustomerById(1L);
        verify(orderLineService).deleteItem(anySet());
        verify(shoppingCartRepository).save(shoppingCart);
    }

}