package Online.Book.Store.checkout.service.implementation;

import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.payment.dto.GetPaymentTransactionRecordRequest;
import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import Online.Book.Store.payment.service.PaymentTransactionService;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ShoppingCartService shoppingCartService;

    private final CustomerService customerService;

    private final PaymentTransactionService paymentTransactionService;

    @Override
    public PaymentTransactionResponseDTO checkOut(PaymentRequestPayload request) {
        log.info("Request to check out {}", request);

        // get customer
        Customer customer = customerService.findCustomerByEmail(request.getCustomerEmail());

        //get order list
        List<OrderLine> orderLineList = customer.getShoppingCart().getOrderLineList();

        // validation

        // create payment trnx
        return paymentTransactionService.createPaymentTransaction(request);
    }

    @Override
    public void updateTransactionRecord(UpdatePaymentTransactionPayload request) {
        log.info("Request to update payment transaction record {}", request);

        //update transaction payment
        paymentTransactionService.updatePaymentTransaction(request);

        // get customer
        Customer customer = paymentTransactionService.validatePaymentTransaction(request.getReferenceNumber()).getCustomer();

        if (Objects.equals(request.getPaymentStatus(), PAYMENTSTATUS.SUCCESSFUL.name())) {

            //clear shopping cart
            shoppingCartService.clearShoppingCart(customer.getShoppingCart().getId());
        }
    }

}
