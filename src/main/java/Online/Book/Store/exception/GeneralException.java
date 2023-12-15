package Online.Book.Store.exception;


import Online.Book.Store.general.enums.ResponseCodeAndMessage;

public class GeneralException extends RuntimeException {

    public GeneralException(String responseCode, String responseMessage) {
        super(responseCode, new Throwable(responseMessage));
    }

    public GeneralException(ResponseCodeAndMessage codeAndMessage) {
        super(codeAndMessage.responseCode, new Throwable(codeAndMessage.responseMessage));
    }
}
