package pl.qceyco.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataBaseExceptionController {

    @ExceptionHandler(value = Exception.class)
    public String handleDBException() {
        return "dataBaseError";
    }


}
