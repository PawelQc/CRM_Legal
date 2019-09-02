package pl.qceyco.app.exception;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = SQLGrammarException.class)
    public String handleDBException(SQLGrammarException e) {
        return "dataBaseError";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleDefaultException(Exception e, Model model) {
        model.addAttribute("error", "Error: something went wrong... Cause: " + e.getMessage());
        return "error";
    }


}
