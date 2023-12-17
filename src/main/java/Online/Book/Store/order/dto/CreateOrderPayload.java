package Online.Book.Store.order.dto;


import Online.Book.Store.checkout.enums.PAYMENTMETHOD;
import Online.Book.Store.order.enums.ORDERSTATUS;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderPayload {

    private String customerEmail;

    private String billingAddress;

    private String phoneNumber;

    private BigDecimal totalCost;

    private PAYMENTMETHOD paymentmethod;

    private ORDERSTATUS orderstatus;
}
