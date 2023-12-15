package Online.Book.Store.payment.service.impl;

import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import Online.Book.Store.payment.model.PaymentTransaction;
import Online.Book.Store.payment.repository.PaymentTransactionRepository;
import Online.Book.Store.payment.service.PaymentTransactionService;
import Online.Book.Store.util.GeneralUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
@AllArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    @Override
    public PaymentTransactionResponseDTO initPayment(PaymentRequestPayload request) {
        log.info("Request to save transaction payment with payload {}", request);

        log.info("Checking out with Transfer {}", request);

        // Simulate transfer checkout
        Date transactionDate = new Date();
        String referenceNumber = request.getCustomerName() + GeneralUtil.generateUniqueReferenceNumber(transactionDate);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentReferenceNumber(referenceNumber);
        paymentTransaction.setTransactionDate(transactionDate);
        paymentTransaction.setAmount(request.getAmount());
        paymentTransaction.setCustomerName(request.getCustomerName());
        paymentTransaction.setPaymentStatus(PAYMENTSTATUS.PENDING);
        paymentTransaction.setPaymentMethod(request.getPaymentmethod());

        // add the orderId

        PaymentTransaction savedPaymentTransaction = paymentTransactionRepository.save(paymentTransaction);

        return getPaymentTransactionDto(savedPaymentTransaction);
    }

    @Override
    public PaymentTransactionResponseDTO updatePayment(String referenceNumber) {
        log.info("Request to update transaction payment with payload {}", referenceNumber);

        PaymentTransaction paymentTransaction = paymentTransactionRepository.findByPaymentReferenceNumber(referenceNumber);

        paymentTransaction.setPaymentStatus(PAYMENTSTATUS.SUCCESSFUL);

        PaymentTransaction savedPaymentTransaction = paymentTransactionRepository.save(paymentTransaction);
        log.info("Successfully updated transaction payment");

        return getPaymentTransactionDto(savedPaymentTransaction);
    }

    private PaymentTransactionResponseDTO getPaymentTransactionDto(PaymentTransaction paymentTransaction) {
        log.info("Converting payment transaction to payment transaction dto");

        PaymentTransactionResponseDTO paymentTransactionResponseDTO = new PaymentTransactionResponseDTO();
        paymentTransactionResponseDTO.setAmount(paymentTransaction.getAmount());
        paymentTransactionResponseDTO.setPaymentReferenceNumber(paymentTransaction.getPaymentReferenceNumber());
        paymentTransactionResponseDTO.setTransactionDate(paymentTransaction.getTransactionDate());
        paymentTransactionResponseDTO.setCustomerName(paymentTransaction.getCustomerName());
        paymentTransactionResponseDTO.setPaymentStatus(paymentTransaction.getPaymentStatus().toString());

        return paymentTransactionResponseDTO;

    }
}
