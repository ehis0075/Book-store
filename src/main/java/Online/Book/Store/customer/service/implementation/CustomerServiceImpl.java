package Online.Book.Store.customer.service.implementation;

import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Slf4j
@Service

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ShoppingCartService shoppingCartService;

    public CustomerServiceImpl(CustomerRepository customerRepository, @Lazy ShoppingCartService shoppingCartService) {
        this.customerRepository = customerRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerPayload request) {
        log.info("Request to create customer {}", request);

        ShoppingCart shoppingCart = new ShoppingCart();

        //save
        ShoppingCart savedShoppingCart = shoppingCartService.save(shoppingCart);

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setShoppingCart(savedShoppingCart);

        Customer savedCustomer = customerRepository.save(customer);

        return getCustomerDto(savedCustomer);
    }

    @Override
    public Customer findCustomerByEmail(String email) {

        return customerRepository.findByEmail(email)
                .orElseThrow(
                        () -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Customer does not exist"));
    }

    private CustomerDTO getCustomerDto(Customer savedCustomer) {

        ShoppingCart shoppingCart = savedCustomer.getShoppingCart();

        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCartDTO(shoppingCart);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(savedCustomer.getId());
        customerDTO.setFirstName(savedCustomer.getFirstName());
        customerDTO.setLastName(savedCustomer.getLastName());
        customerDTO.setEmail(savedCustomer.getEmail());
        customerDTO.setShoppingCartDTO(shoppingCartDTO);

        return customerDTO;
    }
}
