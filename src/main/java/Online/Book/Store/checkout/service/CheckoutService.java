package Online.Book.Store.checkout.service;

import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;

public interface CheckoutService {

    PaymentTransactionResponseDTO checkOut(Long customerId);

    void updateTransactionRecord(UpdatePaymentTransactionPayload request);
}
