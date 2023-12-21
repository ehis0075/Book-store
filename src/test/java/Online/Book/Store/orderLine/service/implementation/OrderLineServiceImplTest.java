package Online.Book.Store.orderLine.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.orderLine.dto.OrderLineDTO;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.repository.OrderLineRepository;
import Online.Book.Store.orderLine.service.OrderLineService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderLineServiceImplTest {

    @Mock
    private OrderLineRepository orderLineRepository;

    @InjectMocks
    private OrderLineService orderLineService;

    @Test
    void testCreateOrderLine() {

        // Given
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        Set<OrderLine> orderLineList = new HashSet<>();

        OrderLine newOrderLine = new OrderLine();
        newOrderLine.setId(1L);
        newOrderLine.setBook(book);
        newOrderLine.setQuantity(1);

        when(orderLineRepository.save(any())).thenReturn(newOrderLine);

        // When
        OrderLine result = orderLineService.createOrderLine(book, orderLineList);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(book, result.getBook());
        assertEquals(1, result.getQuantity());
        assertTrue(orderLineList.contains(result));
    }

    @Test
    void testGetItemsDTO() {
        // Given
        OrderLine orderLine = new OrderLine();
        orderLine.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        orderLine.setBook(book);

        // When
        OrderLineDTO result = orderLineService.getItemsDTO(orderLine);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(book, result.getBook());
    }

    @Test
    void testSaveItem() {
        // Given
        OrderLine orderLine = new OrderLine();

        // When
        orderLineService.saveItem(orderLine);

        // Then
        verify(orderLineRepository, times(1)).save(orderLine);
    }

    @Test
    void testDeleteItem() {
        // Given
        Set<OrderLine> orderLineList = new HashSet<>();
        OrderLine orderLine = new OrderLine();
        orderLine.setId(1L);
        orderLineList.add(orderLine);

        // When
        orderLineService.deleteItem(orderLineList);

        // Then
        verify(orderLineRepository, times(1)).deleteAll(orderLineList);
    }
}