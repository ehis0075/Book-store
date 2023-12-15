package Online.Book.Store.payment.service;


import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;

public interface PaymentTransactionService {

    PaymentTransactionResponseDTO initPayment(PaymentRequestPayload request);

    PaymentTransactionResponseDTO updatePayment(String referenceNumber);
}
