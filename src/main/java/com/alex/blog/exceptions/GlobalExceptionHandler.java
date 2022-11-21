package com.alex.blog.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.alex.blog.dto.ErrorDetails;



// Handler de TODAS las exceptions de nuestra App.
@ControllerAdvice
public class GlobalExceptionHandler {
    

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


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
