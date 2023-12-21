package Online.Book.Store.payment.controller;


import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.payment.dto.PaymentTransactionListDTO;
import Online.Book.Store.payment.service.PaymentTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    private final GeneralService generalService;

    @GetMapping("/{customerId}")
    public Response getPaymentPurchasedTransactionHistory(@PathVariable Long customerId, @RequestParam(defaultValue = "100") int pageSize, @RequestParam(defaultValue = "0") int pageNumber) {

        PaymentTransactionListDTO data = paymentTransactionService.getPaymentPurchasedRecord(customerId, pageNumber, pageSize);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping()
    public Response getAllTransactionHistory(@RequestParam(defaultValue = "100") int pageSize, @RequestParam(defaultValue = "0") int pageNumber) {

        PaymentTransactionListDTO data = paymentTransactionService.getAllPaymentRecord(pageNumber, pageSize);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
