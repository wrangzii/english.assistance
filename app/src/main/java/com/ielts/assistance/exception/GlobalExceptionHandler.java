package com.ielts.assistance.exception;

import com.ielts.assistance.payload.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseObject> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ResponseObject object = new ResponseObject(HttpStatus.BAD_REQUEST.toString(), error);
        log.error(error);
        return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException ex) {
        ResponseObject object = new ResponseObject(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }
}
