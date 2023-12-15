package Online.Book.Store.checkout.service;

import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;

public interface CheckoutService {

    PaymentTransactionResponseDTO processOrder(PaymentRequestPayload request);
}
