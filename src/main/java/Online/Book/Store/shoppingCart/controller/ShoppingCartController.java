package Online.Book.Store.shoppingCart.controller;


import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import Online.Book.Store.orderLine.model.OrderLine;
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
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, "successfully added item to cart");
    }

    @PostMapping("/remove")
    public Response removeBookFromCart(@RequestBody CreatShoppingCartDTO request) {

        shoppingCartService.removeFromCart(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, "successfully removed item from cart");
    }

    @PostMapping("/getCartItems/{customerId}") //
    public Response getCartItems(@PathVariable Long customerId) {

        List<OrderLine> data = shoppingCartService.getAllItems(customerId);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
