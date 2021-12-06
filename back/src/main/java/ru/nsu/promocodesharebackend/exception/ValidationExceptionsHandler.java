package ru.nsu.promocodesharebackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nsu.promocodesharebackend.service.ScheduleParsingService;

@ControllerAdvice
public class ValidationExceptionsHandler {

    static final Logger log =
            LoggerFactory.getLogger(ValidationExceptionsHandler.class);

    //entity fields validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        String validationErrorMessage;
        try {
            validationErrorMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        } catch (NullPointerException e){
            validationErrorMessage = "validation error";
        }
        return new ResponseEntity<>(validationErrorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
