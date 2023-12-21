//package Online.Book.Store.orderLine.service.implementation;
//
//import Online.Book.Store.book.enums.Genre;
//import Online.Book.Store.book.model.Book;
//import Online.Book.Store.book.repository.BookRepository;
//import Online.Book.Store.exception.GeneralException;
//import Online.Book.Store.orderLine.model.OrderLine;
//import Online.Book.Store.orderLine.repository.OrderLineRepository;
//import Online.Book.Store.orderLine.service.OrderLineService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class OrdersLineServiceImplTest {
//
//    @Autowired
//    private OrderLineRepository orderLineRepository;
//    @Autowired
//    private BookRepository bookRepository;
//    @Autowired
//    private OrderLineService orderLineService;
//
//    @Test
//    void testCreateOrderLine() {
//        // Arrange
//        Long bookId = 1L;
//        Book book = new Book("Title", Genre.FICTION, "ISBN123", "Author", "2022", BigDecimal.TEN);
//        OrderLine expectedOrderLine = new OrderLine();
//        expectedOrderLine.setBook(book);
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        when(orderLineRepository.save(any())).thenReturn(expectedOrderLine);
//
//        // Act
//        OrderLine createdOrderLine = orderLineService.createItem(bookId);
//
//        // Assert
//        assertNotNull(createdOrderLine);
//        assertEquals(expectedOrderLine.getBook(), createdOrderLine.getBook());
//        verify(bookRepository, times(1)).findById(bookId);
//        verify(orderLineRepository, times(1)).save(any());
//    }
//
//    @Test
//    void testCreateOrderLineBookNotFound() {
//        // Arrange
//        Long bookId = 1L;
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(GeneralException.class, () -> orderLineService.createOrderLine(bookId));
//        verify(bookRepository, times(1)).findById(bookId);
//        verify(orderLineRepository, times(0)).save(any());
//    }
//
//    @Test
//    void testSaveOrderLine() {
//        // Arrange
//        OrderLine orderLine = new OrderLine();
//        when(orderLineRepository.save(orderLine)).thenReturn(orderLine);
//
//        // Act
//        OrderLine savedOrderLine = orderLineService.saveItem(orderLine);
//
//        // Assert
//        assertNotNull(savedOrderLine);
//        assertEquals(orderLine, savedOrderLine);
//        verify(orderLineRepository, times(1)).save(orderLine);
//    }
//}