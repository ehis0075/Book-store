package Online.Book.Store.shoppingCart.controller;


import Online.Book.Store.book.model.Book;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.service.ShoppingCartService;
import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
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

        ShoppingCartDTO data = shoppingCartService.addToCart(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @PostMapping("/remove")
    public Response removeBookFromCart(@RequestBody CreatShoppingCartDTO request) {

        ShoppingCartDTO data = shoppingCartService.removeFromCart(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @PostMapping("/getBookList/{customerName}")
    public Response getBookListFromCart(@PathVariable String customerName) {

        List<Book> data = shoppingCartService.getAllItems(customerName);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }


}
