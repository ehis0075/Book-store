package Online.Book.Store.payment.dto;

import Online.Book.Store.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTransactionListDTO extends PageableResponseDTO {

    @JsonProperty("paymentTransactions")
    private List<PaymentTransactionDTO> paymentTransactionDTOS;
}
