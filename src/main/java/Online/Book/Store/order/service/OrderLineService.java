package Online.Book.Store.order.service;

import Online.Book.Store.order.model.OrderLine;

public interface OrderLineService {

    OrderLine createOrderLine(Long bookId);

    OrderLine findOrderLineByBookId(Long bookId);
}
