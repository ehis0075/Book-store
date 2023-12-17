package Online.Book.Store.order.dto;


import lombok.Data;

@Data
public class CreateOrderLinePayload {

    private int qty;

    private Long bookId;
}
