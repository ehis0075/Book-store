package Online.Book.Store.orders.controller;


import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.orders.model.OrderListDTO;
import Online.Book.Store.orders.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private final GeneralService generalService;

    @GetMapping("/{customerId}")
    public Response getOrderList(@RequestParam(defaultValue = "100") int pageSize, @RequestParam(defaultValue = "0") int pageNumber) {

        OrderListDTO data = orderService.getAllOrders(pageNumber, pageSize);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }
}
