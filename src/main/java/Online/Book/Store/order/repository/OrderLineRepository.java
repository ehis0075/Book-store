package Online.Book.Store.order.repository;

import Online.Book.Store.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<Order, Long> {


}