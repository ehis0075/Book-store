package Online.Book.Store.payment.dto;


import lombok.Data;

@Data
public class UpdatePaymentTransactionPayload {

    private String referenceNumber;

    private String paymentChannel;

    private String paymentStatus;
}
