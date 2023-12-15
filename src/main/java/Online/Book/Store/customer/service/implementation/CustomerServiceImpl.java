package Online.Book.Store.customer.service.implementation;

import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ShoppingCartService shoppingCartService;

    @Override
    public CustomerDTO createCustomer(CreateCustomerPayload request) {
        log.info("Request to create customer {}", request);

        ShoppingCart shoppingCart = new ShoppingCart();

        //save
        ShoppingCart savedShoppingCart = shoppingCartService.save(shoppingCart);

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setShoppingCart(savedShoppingCart);

        Customer savedCustomer = customerRepository.save(customer);

        return getCustomerDto(savedCustomer);
    }

    private CustomerDTO getCustomerDto(Customer savedCustomer) {

        ShoppingCart shoppingCart = savedCustomer.getShoppingCart();

        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCartDTO(shoppingCart);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(savedCustomer.getId());
        customerDTO.setName(savedCustomer.getName());
        customerDTO.setShoppingCartDTO(shoppingCartDTO);

        return customerDTO;
    }
}
