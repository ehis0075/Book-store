package Online.Book.Store.customer.dto;

import lombok.Data;

@Data
public class CreateCustomerPayload {

    private String firstName;
    private String lastName;
    private String email;
}
