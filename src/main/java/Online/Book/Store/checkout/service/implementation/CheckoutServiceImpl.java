package Online.Book.Store.checkout.service.implementation;

import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orders.dto.OrderDTO;
import Online.Book.Store.orders.service.OrderService;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import Online.Book.Store.payment.enums.PaymentStatus;
import Online.Book.Store.payment.service.PaymentTransactionService;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ShoppingCartService shoppingCartService;

    private final CustomerService customerService;

    private final OrderService orderService;

    private final PaymentTransactionService paymentTransactionService;

    @Override
    public PaymentTransactionResponseDTO checkOut(Long customerId) {
        log.info("Request to check out {}", customerId);

        // get customer
        Customer customer = customerService.findCustomerById(customerId);

        //get order list
        Set<OrderLine> orderLineList = customer.getShoppingCart().getOrderLineList();

        // get total price of items
        BigDecimal totalPrice = calculateTotalPrice(orderLineList);

        // create order
        OrderDTO orderDTO = orderService.createOrder(orderLineList.stream().toList(), customerId);

        // create payment trnx
        return paymentTransactionService.createPaymentTransaction(customerId, totalPrice, orderDTO.getId());
    }

    @Override
    public void updateTransactionRecord(UpdatePaymentTransactionPayload request) {
        log.info("Request to update payment transaction record {}", request);

        //update transaction payment
        paymentTransactionService.updatePaymentTransaction(request);

        // get customer
        Customer customer = paymentTransactionService.validatePaymentTransaction(request.getReferenceNumber()).getCustomer();

        if (Objects.equals(request.getPaymentStatus(), PaymentStatus.SUCCESSFUL.name())) {

            //clear shopping cart
            shoppingCartService.clearShoppingCart(customer.getShoppingCart().getId());
        }
    }

    public BigDecimal calculateTotalPrice(Set<OrderLine> orderLineList) {
        log.info("Calculating total price for {}", orderLineList);

        return orderLineList.stream()
                .collect(Collectors.groupingBy(OrderLine::getBook,
                        Collectors.mapping(orderLine ->
                                        orderLine.getBook().getPrice().multiply(BigDecimal.valueOf(orderLine.getQuantity())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
