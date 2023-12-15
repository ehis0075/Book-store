package Online.Book.Store.checkout.service.implementation;

import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
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


@Slf4j
@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ShoppingCartService shoppingCartService;

    private final CustomerRepository customerRepository;

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionService paymentTransactionService;

    @Override
    public PaymentTransactionResponseDTO processOrder(PaymentRequestPayload request) {
        log.info("Request to check out {}", request);

        //initiate payment
        PaymentTransactionResponseDTO paymentPayload = paymentTransactionService.initPayment(request);

        // make payment
        PaymentTransactionResponseDTO paymentTransactionResponse = makePayment(request);

        //update payment
        PaymentTransactionResponseDTO paymentTransactionResponseDTO = paymentTransactionService.updatePayment(paymentTransactionResponse.getPaymentReferenceNumber());

        // get customer
        Customer customer = customerRepository.findByName(request.getCustomerName());

        //clear shopping cart
        shoppingCartService.clearShoppingCart(customer.getShoppingCart().getId());

        return paymentTransactionResponseDTO;
    }

    public PaymentTransactionResponseDTO makePayment(PaymentRequestPayload request) {
        log.info("Request to make payment {}", request);

        PaymentTransactionResponseDTO paymentTransactionResponseDTO;

        switch (request.getPaymentmethod()) {
            case WEB:
                paymentTransactionResponseDTO = checkoutWithWeb(request);
                break;
            case USSD:
                paymentTransactionResponseDTO = checkoutWithUSSD(request);
                break;
            case TRANSFER:
                paymentTransactionResponseDTO = checkoutWithTransfer(request);
                break;
            default:
                throw new IllegalArgumentException("Invalid checkout option");
        }
        return paymentTransactionResponseDTO;
    }

    public PaymentTransactionResponseDTO checkoutWithWeb(PaymentRequestPayload request) {
        log.info("Checking out with WEB");

        // Simulate web checkout
        return new PaymentTransactionResponseDTO();
    }

    public PaymentTransactionResponseDTO checkoutWithUSSD(PaymentRequestPayload request) {
        log.info("Checking out with USSD");

        // Simulate USSD checkout
        return new PaymentTransactionResponseDTO();
    }

    public PaymentTransactionResponseDTO checkoutWithTransfer(PaymentRequestPayload request) {
        log.info("Checking out with Transfer {}", request);

        // Simulate transfer checkout
        Date transactionDate = new Date();
        String referenceNumber = request.getCustomerName() + GeneralUtil.generateUniqueReferenceNumber(transactionDate);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentReferenceNumber(referenceNumber);
        paymentTransaction.setTransactionDate(transactionDate);
        paymentTransaction.setAmount(request.getAmount());
        paymentTransaction.setCustomerName(request.getCustomerName());
        paymentTransaction.setPaymentStatus(PAYMENTSTATUS.SUCCESSFUL);

        PaymentTransaction savedPaymentTransaction = paymentTransactionRepository.save(paymentTransaction);

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
        paymentTransactionResponseDTO.setPaymentStatus(paymentTransactionResponseDTO.getPaymentStatus());

        return paymentTransactionResponseDTO;
    }
}
