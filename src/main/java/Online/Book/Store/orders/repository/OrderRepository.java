package Online.Book.Store.orders.repository;

import Online.Book.Store.orders.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomer_Id(Long customerId);

    Page<Orders> findAll(Pageable pageable);

}
