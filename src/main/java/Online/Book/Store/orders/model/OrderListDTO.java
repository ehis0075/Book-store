package Online.Book.Store.orders.model;

import Online.Book.Store.general.dto.PageableResponseDTO;
import Online.Book.Store.orders.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderListDTO extends PageableResponseDTO {

    @JsonProperty("Orders")
    private List<OrderDTO> orderList;
}
