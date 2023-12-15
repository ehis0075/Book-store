package Online.Book.Store.general.service;



import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface GeneralService {
    boolean isStringInvalid(String string);

    Response prepareResponse(ResponseCodeAndMessage codeAndMessage, Object data);

    Response prepareResponse(String responseCode, String responseMessage, Object data);

    Pageable getPageableObject(int size, int page);

    void createDTOFromModel(Object from, Object to);

    Response getResponse(String responseCode, String responseMessage, Object data);

}
