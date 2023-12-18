package Online.Book.Store.payment.controller;


import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.payment.model.PaymentTransaction;
import Online.Book.Store.payment.service.PaymentTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    private final GeneralService generalService;

    @GetMapping("/{customerEmail}")
    public Response getPaymentTransactionHistory(@PathVariable String customerEmail, @RequestParam(defaultValue = "0") int pageSize, @RequestParam(defaultValue = "100") int pageNumber) {

        Page<PaymentTransaction> data = paymentTransactionService.getPaymentTransactionRecord(customerEmail, pageNumber, pageSize);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
