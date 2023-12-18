package Online.Book.Store.customer.service;

import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.customer.model.Customer;

public interface CustomerService {

    CustomerDTO createCustomer(CreateCustomerPayload request);

    Customer findCustomerByEmail(String email);
}
