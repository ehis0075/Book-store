package Online.Book.Store.orderLine.repository;

import Online.Book.Store.orderLine.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
