package Online.Book.Store.exception;

import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GeneralService generalService;

    public ExceptionController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @ExceptionHandler({GeneralException.class})
    public final ResponseEntity<?> handleException(Exception ex) {
        logger.info("Error occurred, error message is {}", ex.getMessage());

        if (ex instanceof GeneralException) {
            Response response = generalService.prepareResponse(ex.getMessage(), ex.getCause().getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = generalService.prepareResponse(ResponseCodeAndMessage.AN_ERROR_OCCURRED_96, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
