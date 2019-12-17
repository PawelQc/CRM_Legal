package pl.qceyco.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(DefaultExceptionHandler.class.getName());

    @ExceptionHandler(value = Exception.class)
    public String handleDefaultException(Exception e, Model model) {
        model.addAttribute("error", "Error: something went wrong...");
        model.addAttribute("errorMessage", "Cause: " + e.getMessage());
        LOGGER.error(e.getMessage());       //todo logger nie zapisuje logu do pliku...
        return "error";
    }


}
