package Online.Book.Store.order.service;

import Online.Book.Store.order.dto.CreateOrderLinePayload;
import Online.Book.Store.order.model.OrderLine;

public interface OrderLineService {

    OrderLine createOrderLine(CreateOrderLinePayload request);

    OrderLine findByBookTitle(String bookTitle);
}
