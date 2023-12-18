package Online.Book.Store.orderLine.service;

import Online.Book.Store.orderLine.model.OrderLine;

public interface OrderLineService {

    OrderLine createOrderLine(Long bookId);

    OrderLine findOrderLineByBookId(Long bookId);
}
