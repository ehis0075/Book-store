package Online.Book.Store.payment.service.impl;

import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import Online.Book.Store.payment.enums.CHANNEL;
import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import Online.Book.Store.payment.model.PaymentTransaction;
import Online.Book.Store.payment.repository.PaymentTransactionRepository;
import Online.Book.Store.payment.service.PaymentTransactionService;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import Online.Book.Store.util.GeneralUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final CustomerService customerService;

    private final ShoppingCartService shoppingCartService;
    private static final String PAYMENT_GATEWAY_URL = "gatewayUrl";

    @Override
    public PaymentTransactionResponseDTO createPaymentTransaction(PaymentRequestPayload request) {
        log.info("Request to create transaction record with payload : {} ", request);

        String referenceNumber = request.getCustomerEmail() + GeneralUtil.generateUniqueReferenceNumber(new Date());

        // get customer
        Customer customer = customerService.findCustomerByEmail(request.getCustomerEmail());

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentReferenceNumber(referenceNumber);
        paymentTransaction.setTransactionDate(new Date());
        paymentTransaction.setAmount(request.getAmount());
        paymentTransaction.setCustomer(customer);
        paymentTransaction.setPaymentStatus(PAYMENTSTATUS.PENDING);

        savePaymentTransaction(paymentTransaction);

        return new PaymentTransactionResponseDTO(referenceNumber, paymentTransaction.getAmount(), PAYMENT_GATEWAY_URL);
    }

    public void savePaymentTransaction(PaymentTransaction paymentTransaction) {
        paymentTransactionRepository.save(paymentTransaction);
    }

    @Override
    public void updatePaymentTransaction(UpdatePaymentTransactionPayload requestPayload) {
        log.info("Request to update transaction payment with payload {}", requestPayload);

        PaymentTransaction paymentTransaction = paymentTransactionRepository.findByPaymentReferenceNumber(requestPayload.getReferenceNumber())
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Payment transaction not found"));

        paymentTransaction.setPaymentChannel(CHANNEL.valueOf(requestPayload.getPaymentChannel()));
        paymentTransaction.setPaymentStatus(PAYMENTSTATUS.valueOf(requestPayload.getPaymentStatus()));

        // get customer
        Long shoppingCartId = paymentTransaction.getCustomer().getShoppingCart().getId();

        savePaymentTransaction(paymentTransaction);
        log.info("Successfully updated transaction payment");

        // clear shopping cart
        if (Objects.equals(requestPayload.getPaymentStatus(), PAYMENTSTATUS.SUCCESSFUL.name())) {
            shoppingCartService.clearShoppingCart(shoppingCartId);
        }
    }

    @Override
    public PaymentTransaction validatePaymentTransaction(String refNumber) {

        return paymentTransactionRepository.findByPaymentReferenceNumber(refNumber)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Transaction record Not Found"));
    }

}
