package Online.Book.Store.payment.service;


import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionListDTO;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import Online.Book.Store.payment.model.PaymentTransaction;
import org.springframework.data.domain.Page;

public interface PaymentTransactionService {

    PaymentTransactionResponseDTO createPaymentTransaction(PaymentRequestPayload request);

    void updatePaymentTransaction(UpdatePaymentTransactionPayload requestPayload);

    PaymentTransaction validatePaymentTransaction(String refNumber);

    PaymentTransactionListDTO getPaymentTransactionRecord(String customerEmail, int pageNumber, int pageSize);
}
