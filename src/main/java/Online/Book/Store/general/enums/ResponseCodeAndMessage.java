package Online.Book.Store.general.enums;

public enum ResponseCodeAndMessage {

    SUCCESSFUL_0("0", "Successful"),
    RECORD_NOT_FOUND_88("88", "Record not found"),
    INCOMPLETE_PARAMETERS_91("91", "Incomplete parameters"),
    AN_ERROR_OCCURRED_96("96", "An error occurred"),
    ALREADY_EXIST_86("86", "Already exist");

    public String responseCode;
    public String responseMessage;

    ResponseCodeAndMessage(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
