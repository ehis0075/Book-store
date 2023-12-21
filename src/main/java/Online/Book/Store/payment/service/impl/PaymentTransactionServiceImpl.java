package Online.Book.Store.payment.service.impl;

import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.orders.model.Orders;
import Online.Book.Store.orders.service.OrderService;
import Online.Book.Store.payment.dto.*;
import Online.Book.Store.payment.enums.Channel;
import Online.Book.Store.payment.enums.PaymentStatus;
import Online.Book.Store.payment.model.PaymentTransaction;
import Online.Book.Store.payment.repository.PaymentTransactionRepository;
import Online.Book.Store.payment.service.PaymentTransactionService;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import Online.Book.Store.util.GeneralUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private static final String PAYMENT_GATEWAY_URL = "gatewayUrl";
    private static final PaymentStatus PAYMENT_STATUS = PaymentStatus.SUCCESSFUL;

    private final CustomerService customerService;

    private final ShoppingCartService shoppingCartService;

    private final OrderService orderService;

    private final PaymentTransactionRepository paymentTransactionRepository;


    @Override
    public PaymentTransactionResponseDTO createPaymentTransaction(Long customerId, BigDecimal totalAmount, Long orderId) {
        log.info("Request to create transaction record with payload : {} {} {} ", customerId, totalAmount, orderId);

        String referenceNumber = "REF" + GeneralUtil.generateUniqueReferenceNumber(new Date());

        // get customer
        Customer customer = customerService.findCustomerById(customerId);

        //get saved Order
        Orders orders = orderService.findOrderById(orderId);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentReferenceNumber(referenceNumber);
        paymentTransaction.setTransactionDate(new Date());
        paymentTransaction.setAmount(totalAmount);
        paymentTransaction.setCustomer(customer);
        paymentTransaction.setPaymentStatus(PaymentStatus.PENDING);
        paymentTransaction.setOrders(orders);

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

        paymentTransaction.setPaymentChannel(Channel.valueOf(requestPayload.getPaymentChannel()));
        paymentTransaction.setPaymentStatus(PaymentStatus.valueOf(requestPayload.getPaymentStatus()));

        // get customer
        Long shoppingCartId = paymentTransaction.getCustomer().getShoppingCart().getId();

        savePaymentTransaction(paymentTransaction);
        log.info("Successfully updated transaction payment");

        // clear shopping cart
        if (Objects.equals(requestPayload.getPaymentStatus(), PaymentStatus.SUCCESSFUL.name())) {
            shoppingCartService.clearShoppingCart(shoppingCartId);
        }
    }

    @Override
    public PaymentTransaction validatePaymentTransaction(String refNumber) {

        return paymentTransactionRepository.findByPaymentReferenceNumber(refNumber)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Transaction record Not Found"));
    }

    @Override
    public PaymentTransactionListDTO getPaymentPurchasedRecord(Long customerId, int pageNumber, int pageSize) {
        log.info("Request to get all purchased payment transaction records for {}", customerId);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<PaymentTransaction> transactionPage = paymentTransactionRepository.findByPaymentStatusAndCustomer_Id(PAYMENT_STATUS, customerId, pageable);

        return getPaymentTransactionListDTO(transactionPage);
    }

    @Override
    public PaymentTransactionListDTO getAllPaymentRecord(int pageNumber, int pageSize) {
        log.info("Request to get all payment transaction records {} {}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<PaymentTransaction> transactionPage = paymentTransactionRepository.findAll(pageable);

        return getPaymentTransactionListDTO(transactionPage);
    }

    private PaymentTransactionListDTO getPaymentTransactionListDTO(Page<PaymentTransaction> transactionPage) {
        PaymentTransactionListDTO listDTO = new PaymentTransactionListDTO();

        List<PaymentTransaction> transactionList = transactionPage.getContent();
        if (transactionPage.getContent().size() > 0) {
            listDTO.setHasNextRecord(transactionPage.hasNext());
            listDTO.setTotalCount((int) transactionPage.getTotalElements());
        }

        List<PaymentTransactionDTO> paymentTransactionDTOS = convertToPaymentTransactionDTOList(transactionList);
        listDTO.setPaymentTransactionDTOS(paymentTransactionDTOS);
        return listDTO;
    }

    private List<PaymentTransactionDTO> convertToPaymentTransactionDTOList(List<PaymentTransaction> paymentTransactions) {
        log.info("Converting Payment transaction List to Merchant DTO List");

        return paymentTransactions.stream().map(PaymentTransaction::getPaymentTransactionDTO).collect(Collectors.toList());
    }

}
