package Online.Book.Store.orderLine.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.orderLine.dto.OrderLineDTO;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.repository.OrderLineRepository;
import Online.Book.Store.orderLine.service.OrderLineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


@Slf4j
@Service
@AllArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Override
    public OrderLine createOrderLine(Book book, Set<OrderLine> orderLineList) {
        log.info("Request to create item for book {} {}", book, orderLineList);

        OrderLine newOrderLine = new OrderLine();
        newOrderLine.setBook(book);
        newOrderLine.setCount(1);
        orderLineList.add(newOrderLine);

        return orderLineRepository.save(newOrderLine);
    }

    @Override
    public OrderLineDTO getItemsDTO(OrderLine item) {

        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setId(item.getId());
        orderLineDTO.setBook(item.getBook());

        return orderLineDTO;
    }

    @Override
    public void saveItem(OrderLine orderLine) {

        orderLineRepository.save(orderLine);
    }

    @Override
    public void deleteItem(Set<OrderLine> orderLineList) {
        log.info("Request to delete item {}", orderLineList);

        orderLineRepository.deleteAll(orderLineList);
    }
}
