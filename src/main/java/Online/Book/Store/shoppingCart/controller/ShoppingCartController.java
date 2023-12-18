package Online.Book.Store.shoppingCart.controller;


import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/shoppingCarts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final GeneralService generalService;

    @PostMapping("/add")
    public Response addBookToCart(@RequestBody CreatShoppingCartDTO request) {

        shoppingCartService.addToCart(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, "successfully added to cart");
    }

    @PostMapping("/remove")
    public Response removeBookFromCart(@RequestBody CreatShoppingCartDTO request) {

        shoppingCartService.removeFromCart(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, "successful");
    }

    @PostMapping("/getBookList/{customerEmail}")
    public Response getBookListFromCart(@PathVariable String customerEmail) {

        List<OrderLine> data = shoppingCartService.getAllItems(customerEmail);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }


}
