package Online.Book.Store.shoppingCart.repository;

import Online.Book.Store.shoppingCart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
