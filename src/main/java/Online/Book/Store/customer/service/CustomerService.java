package Online.Book.Store.customer.service;

import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO createCustomer(CreateCustomerPayload request);
}
