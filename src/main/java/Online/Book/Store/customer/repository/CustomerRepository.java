package Online.Book.Store.customer.repository;

import Online.Book.Store.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByName(String name);
}
