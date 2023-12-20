//package Online.Book.Store.customer.service.implementation;
//
//import Online.Book.Store.customer.dto.CreateCustomerPayload;
//import Online.Book.Store.customer.dto.CustomerDTO;
//import Online.Book.Store.customer.model.Customer;
//import Online.Book.Store.customer.repository.CustomerRepository;
//import Online.Book.Store.exception.GeneralException;
//import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
//import Online.Book.Store.shoppingCart.model.ShoppingCart;
//import Online.Book.Store.shoppingCart.service.ShoppingCartService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class CustomerServiceTest {
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    @Mock
//    private ShoppingCartService shoppingCartService;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateCustomer() {
//
//        // Arrange
//        CreateCustomerPayload request = new CreateCustomerPayload();
//        request.setFirstName("John");
//        request.setLastName("Doe");
//        request.setEmail("john.doe@example.com");
//
//        ShoppingCart savedShoppingCart = new ShoppingCart();
//        when(shoppingCartService.save(any())).thenReturn(savedShoppingCart);
//
//        Customer savedCustomer = new Customer();
//        savedCustomer.setId(1L);
//        savedCustomer.setFirstName("John");
//        savedCustomer.setLastName("Doe");
//        savedCustomer.setEmail("john.doe@example.com");
//        savedCustomer.setShoppingCart(savedShoppingCart);
//        when(customerRepository.save(any())).thenReturn(savedCustomer);
//
//        // Act
//        CustomerDTO result = customerService.createCustomer(request);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(savedCustomer.getId(), result.getId());
//        assertEquals(savedCustomer.getFirstName(), result.getFirstName());
//        assertEquals(savedCustomer.getLastName(), result.getLastName());
//        assertEquals(savedCustomer.getEmail(), result.getEmail());
//    }
//
//    @Test
//    void testSaveShoppingCart() {
//        // Arrange
//        ShoppingCart shoppingCart = new ShoppingCart();
//        when(shoppingCartService.save(any())).thenReturn(shoppingCart);
//
//        // Act
//        ShoppingCart result = shoppingCartService.save(new ShoppingCart());
//
//        // Assert
//        assertNotNull(result);
//    }
//
//    @Test
//    void testFindCustomerByEmail() {
//        // Arrange
//        String email = "john.doe@example.com";
//        Customer expectedCustomer = new Customer();
//        expectedCustomer.setId(1L);
//        expectedCustomer.setFirstName("John");
//        expectedCustomer.setLastName("Doe");
//        expectedCustomer.setEmail(email);
//
//        // Mocking the customerRepository behavior
//        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(expectedCustomer));
//
//        // Mocking the shoppingCartService behavior
//        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO(); // Provide relevant details
//        when(shoppingCartService.getShoppingCartDTO(expectedCustomer.getShoppingCart()))
//                .thenReturn(expectedShoppingCartDTO);
//
//        // Act
//        Customer result = customerService.findCustomerById(email);
//
//        CustomerDTO customerDTO = customerService.getCustomerDto(result);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(expectedCustomer.getId(), result.getId());
//        assertEquals(expectedCustomer.getFirstName(), result.getFirstName());
//        assertEquals(expectedCustomer.getLastName(), result.getLastName());
//        assertEquals(expectedCustomer.getEmail(), result.getEmail());
//        assertEquals(expectedShoppingCartDTO, customerDTO.getShoppingCartDTO());
//    }
//
//    @Test
//    void testFindCustomerByEmailNotFound() {
//        // Arrange
//        String email = "nonexistent@example.com";
//
//        // Mocking the customerRepository behavior
//        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(GeneralException.class, () -> customerService.findCustomerById(email));
//    }
//
//    @Test
//    void testGetCustomerDto() {
//
//        // Arrange
//        Customer savedCustomer = new Customer();
//        savedCustomer.setId(1L);
//        savedCustomer.setFirstName("John");
//        savedCustomer.setLastName("Doe");
//        savedCustomer.setEmail("john.doe@example.com");
//
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setId(2L);
//
//        // Mocking the shoppingCartService behavior
//        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO(); // Provide relevant details
//        when(shoppingCartService.getShoppingCartDTO(shoppingCart))
//                .thenReturn(expectedShoppingCartDTO);
//
//        // Act
//        CustomerDTO result = customerService.getCustomerDto(savedCustomer);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(savedCustomer.getId(), result.getId());
//        assertEquals(savedCustomer.getFirstName(), result.getFirstName());
//        assertEquals(savedCustomer.getLastName(), result.getLastName());
//        assertEquals(savedCustomer.getEmail(), result.getEmail());
//    }
//
//
//}