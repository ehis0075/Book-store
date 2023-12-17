package Online.Book.Store.order.dto;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.order.enums.ORDERSTATUS;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class OrderDTO {

    private Long id;

    private String billingAddress;

    private String phoneNumber;

    private Customer customer;

    private List<Book> books;

    private BigDecimal totalCost;

    private PAYMENTMETHOD paymentmethod;

    private Date orderDate;

    private ORDERSTATUS orderstatus;
}
