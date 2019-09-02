package pl.qceyco.app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Fajnie, że pobawiłeś się w obsługę błędów
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleDefaultException(Exception e, Model model) {
        model.addAttribute("error", "Error: something went wrong... Cause: " + e.getMessage());
        return "error";
    }


}
