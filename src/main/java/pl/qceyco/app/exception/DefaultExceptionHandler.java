package pl.qceyco.app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public String handleDBException(SQLSyntaxErrorException e) {
        return "dataBaseError";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleDefaultException(Exception e, Model model) {
        model.addAttribute("error", "Error: something went wrong... Cause: " + e.getMessage());
        return "error";
    }


}
