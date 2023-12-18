package Online.Book.Store.customer.repository;

import Online.Book.Store.customer.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void findByEmail() {

        String email = "ehisjude@gmail.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("ehis");
        customer.setLastName("jude");

        customerRepository.save(customer);

        //when
        Optional<Customer> retrievedCustomer = customerRepository.findByEmail(email);

        //assert
        assertNotNull(retrievedCustomer);
        assertEquals(email, retrievedCustomer.get().getEmail());
    }
}