package Online.Book.Store.orderLine.service;

import Online.Book.Store.orderLine.model.OrderLine;

import java.util.List;

public interface OrderLineService {

    OrderLine createOrderLine(Long bookId);

    OrderLine saveOrderLine(OrderLine orderLine);

    void deleteOrderLine(List<OrderLine> orderLineList);
}
