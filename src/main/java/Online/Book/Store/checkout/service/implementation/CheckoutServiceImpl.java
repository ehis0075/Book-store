package Online.Book.Store.checkout.service.implementation;

import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.service.BookService;
import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.order.dto.OrderDTO;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.order.service.OrderService;
import Online.Book.Store.payment.dto.PaymentRequestPayload;
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
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final BookService bookService;

    private final ShoppingCartService shoppingCartService;

    private final CustomerRepository customerRepository;

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionService paymentTransactionService;

    private final OrderService orderService;

    @Override
    public PaymentTransactionResponseDTO processOrder(PaymentRequestPayload request, CreateOrderPayload orderPayload) {
        log.info("Request to check out {}", request);

        // get customer
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail());

        //get BookList
        List<OrderLine> orderLineList = customer.getShoppingCart().getOrderLineList();

        // validate books
        bookService.validateBook(orderLineList);

        // Create an order record in the database with information like customer ID, order date, total cost, payment details, and status.
        OrderDTO orderDTO = orderService.createOrder(orderPayload);

        //initiate payment
        PaymentTransactionResponseDTO paymentPayload = paymentTransactionService.initPayment(request);

        PaymentTransactionResponseDTO paymentTransactionResponseDTO = null;

        try {

            // Process payment
            PaymentTransactionResponseDTO paymentTransactionResponse = makePayment(request);

            if (paymentTransactionResponse.getPaymentStatus().equals("SUCCESSFUL")) {

                //update payment
                paymentTransactionResponseDTO = paymentTransactionService.updatePayment(paymentTransactionResponse.getPaymentReferenceNumber());


                //deduct stock : Update the book's stock count in the database by subtracting the ordered quantity.
                deductBookStock(orderLineList);

                //clear shopping cart
                shoppingCartService.clearShoppingCart(customer.getShoppingCart().getId());
            }

        } catch (Exception e) {
            log.error(e.getMessage());

        }

        // get payment trnx response
        return paymentTransactionResponseDTO;
    }

    public void deductBookStock(List<OrderLine> orderLineList) {
        log.info("Deducting stock for ordered books");

        for (OrderLine orderLine : orderLineList) {
            Book book = orderLine.getBook();
            int quantity = orderLine.getQuantity();
            deductStock(book, quantity);
        }
    }

    private void deductStock(Book book, int quantity) {
        int currentStock = book.getStockCount();
        if (currentStock < quantity) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "Insufficient stock for book: " + book.getTitle());
        }

        book.setStockCount(currentStock - quantity);

        // update the book in the database
        bookService.saveBook(book);
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
        String referenceNumber = request.getCustomerEmail() + GeneralUtil.generateUniqueReferenceNumber(transactionDate);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentReferenceNumber(referenceNumber);
        paymentTransaction.setTransactionDate(transactionDate);
//        paymentTransaction.setAmount(request.getAmount());
        paymentTransaction.setCustomerName(request.getCustomerEmail());
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
