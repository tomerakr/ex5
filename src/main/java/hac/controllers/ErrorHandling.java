package hac.controllers;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ErrorHandling class handles error-related requests and exception handling.
 */
@Controller
public class ErrorHandling {

    /**
     * Handles the request for the 403 Forbidden page.
     *
     * @return The name of the 403 view.
     */
    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }

    /**
     * Handles exceptions thrown during the execution of the Spring Security application.
     *
     * @param ex    The exception that occurred.
     * @param model The model to add attributes to.
     * @return The name of the error view.
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {

        //logger.error("Exception during execution of Spring Security application", ex);
        String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
