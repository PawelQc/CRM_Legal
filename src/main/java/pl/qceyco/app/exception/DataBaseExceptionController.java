package pl.qceyco.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class DataBaseExceptionController {

    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public String handleDBException() {
        return "dataBaseError";
    }


}
