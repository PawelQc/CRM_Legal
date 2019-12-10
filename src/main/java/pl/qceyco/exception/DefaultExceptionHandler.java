//package pl.qceyco.exception;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class DefaultExceptionHandler {
//
//    @ExceptionHandler(value = Exception.class)
//    public String handleDefaultException(Exception e, Model model) {
//        model.addAttribute("error", "Error: something went wrong...");
//        model.addAttribute("errorMessage", "Cause: " + e.getMessage());
//        return "error";
//    }
//
//
//}
