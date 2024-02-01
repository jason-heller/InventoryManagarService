package jh.github.com.invmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InventoryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InventoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String InventoryNotFoundHandler(InventoryNotFoundException e) {
        return e.getMessage();
    }
}