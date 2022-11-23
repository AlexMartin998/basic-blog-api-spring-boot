package com.alex.blog.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alex.blog.dto.ErrorDetails;




@ControllerAdvice   // Handler de TODAS las exceptions de nuestra App.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {    // extends para crear el handler del @Valid
    

    @ExceptionHandler(ResourceNotFoundExcepion.class)   // maneja solo las excepciones anotadas en el ()
    public ResponseEntity<ErrorDetails> handlerResourceNotFoudException(ResourceNotFoundExcepion exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetails> hanlderBlogAppException(BlogAppException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription((false)));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> hanlderBadCredentialsException(BadCredentialsException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                "There was a problem logging in. Check your email and password or create an account.",
                webRequest.getDescription((false)));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> hanlderBadCredentialsException(AccessDeniedException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                "User without required permissions!",
                webRequest.getDescription((false)));

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // handler de los errors del @Valid <- BindingResult
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, status);
    }

}
