package Online.Book.Store.checkout.controller;


import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import Online.Book.Store.payment.dto.UpdatePaymentTransactionPayload;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    private final GeneralService generalService;

    @PostMapping("/checkOut/{customerId}")
    public Response checkOut(@PathVariable Long customerId) {

        PaymentTransactionResponseDTO data = checkoutService.checkOut(customerId);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @PostMapping("/update")
    public Response addBook(@RequestBody UpdatePaymentTransactionPayload request) {

        checkoutService.updateTransactionRecord(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, "Payment transaction record updated successfully");
    }

}
