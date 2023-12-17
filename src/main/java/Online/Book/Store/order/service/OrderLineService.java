package Online.Book.Store.order.service;

import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.dto.OrderLineDTO;

public interface OrderLineService {

    OrderLineDTO createOrderLine(CreateOrderLinePayload request);
}
