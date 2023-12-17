package Online.Book.Store.order.service;

import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.order.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(CreateOrderPayload request);
}
