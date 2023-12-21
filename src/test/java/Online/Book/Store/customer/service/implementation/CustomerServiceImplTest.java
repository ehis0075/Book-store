package Online.Book.Store.customer.service.implementation;

import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Given
        CreateCustomerPayload request = new CreateCustomerPayload();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");

        ShoppingCart savedShoppingCart = new ShoppingCart();
        when(shoppingCartService.save(any())).thenReturn(savedShoppingCart);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");
        savedCustomer.setEmail("john.doe@example.com");
        savedCustomer.setShoppingCart(savedShoppingCart);
        when(customerRepository.save(any())).thenReturn(savedCustomer);

        // When
        CustomerDTO result = customerService.createCustomer(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertNotNull(result.getShoppingCartDTO());
    }

    @Test
    void testFindCustomerById() {
        // Given
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));

        // When
        Customer result = customerService.findCustomerById(customerId);

        // Then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
    }

    @Test
    void testFindCustomerById_CustomerNotFound() {
        // Given
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

        // When and Then
        assertThrows(GeneralException.class, () -> customerService.findCustomerById(customerId));
    }

    @Test
    void testGetCustomerDto() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartService.getShoppingCartDTO(any())).thenReturn(new ShoppingCartDTO());

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");
        savedCustomer.setEmail("john.doe@example.com");
        savedCustomer.setShoppingCart(shoppingCart);

        // When
        CustomerDTO result = customerService.getCustomerDto(savedCustomer);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertNotNull(result.getShoppingCartDTO());
    }

}