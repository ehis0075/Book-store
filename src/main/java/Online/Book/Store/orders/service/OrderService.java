package Online.Book.Store.orders.service;

import Online.Book.Store.orders.model.OrderListDTO;
import Online.Book.Store.orders.model.Orders;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orders.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(List<OrderLine> itemList, Long customerId);

    OrderDTO getOrderDTO(Orders orders);

    Orders findOrderById(Long id);

    OrderListDTO getAllOrders(int pageNumber, int pageSize);
}
