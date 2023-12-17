package Online.Book.Store.checkout.controller;


import Online.Book.Store.checkout.service.CheckoutService;
import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.order.dto.CreateOrderPayload;
import Online.Book.Store.payment.dto.PaymentRequestPayload;
import Online.Book.Store.payment.dto.PaymentTransactionResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    private final GeneralService generalService;

    @PostMapping("/processOrder")
    public Response addBook(@RequestBody PaymentRequestPayload request, CreateOrderPayload payload) {

        PaymentTransactionResponseDTO data = checkoutService.processOrder(request, payload);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
