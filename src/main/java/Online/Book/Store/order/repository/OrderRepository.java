package Online.Book.Store.order.repository;

import Online.Book.Store.order.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

}
