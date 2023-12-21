package Online.Book.Store.orderLine.service;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.orderLine.dto.OrderLineDTO;
import Online.Book.Store.orderLine.model.OrderLine;

import java.util.Set;

public interface OrderLineService {

    OrderLine createOrderLine(Book bookId, Set<OrderLine> orderLineList);

    OrderLineDTO getItemsDTO(OrderLine item);

    void saveItem(OrderLine orderLine);

    void deleteItem(Set<OrderLine> orderLineList);
}
