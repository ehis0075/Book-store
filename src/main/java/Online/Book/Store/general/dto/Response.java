package Online.Book.Store.general.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response {
    private String responseCode;

    private String responseMessage;

    private Object data;
}
