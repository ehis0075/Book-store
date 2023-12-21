package Online.Book.Store.payment.service;


import Online.Book.Store.payment.dto.PaymentTransactionListDTO;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import Online.Book.Store.payment.model.PaymentTransaction;

import java.math.BigDecimal;

public interface PaymentTransactionService {

    PaymentTransactionResponseDTO createPaymentTransaction(Long customerId, BigDecimal totalAmount, Long orderId);

    void updatePaymentTransaction(UpdatePaymentTransactionPayload requestPayload);

    PaymentTransaction validatePaymentTransaction(String refNumber);

    PaymentTransactionListDTO getPaymentPurchasedRecord(Long customerId, int pageNumber, int pageSize);

    PaymentTransactionListDTO getAllPaymentRecord(int pageNumber, int pageSize);
}
