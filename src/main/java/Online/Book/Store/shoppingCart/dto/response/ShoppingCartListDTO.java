package Online.Book.Store.shoppingCart.dto.response;

import Online.Book.Store.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartListDTO extends PageableResponseDTO {

    @JsonProperty("shoppingCarts")
    private List<ShoppingCartDTO> shoppingCartDTOList;
}