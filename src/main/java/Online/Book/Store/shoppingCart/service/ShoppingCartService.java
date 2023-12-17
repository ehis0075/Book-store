package Online.Book.Store.shoppingCart.service;

import Online.Book.Store.order.model.OrderLine;
import Online.Book.Store.shoppingCart.dto.request.CreatShoppingCartDTO;
import Online.Book.Store.shoppingCart.dto.response.ShoppingCartDTO;
import Online.Book.Store.shoppingCart.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartDTO addToCart(CreatShoppingCartDTO request);

    ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart);

    ShoppingCartDTO removeFromCart(CreatShoppingCartDTO request);

    List<OrderLine> getAllItems(String customerEmail);

    ShoppingCart save(ShoppingCart shoppingCart);

    void clearShoppingCart(Long shoppingCartId);
}
