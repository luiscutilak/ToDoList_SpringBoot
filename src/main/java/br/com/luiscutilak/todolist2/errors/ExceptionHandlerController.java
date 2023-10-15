package br.com.luiscutilak.todolist2.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    //Método que irá receber a excessão de erro para tratar com usuario de uma forma personalizada.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableExcpetion(HttpMessageNotReadableException e) {
         //Abaixo ele retorna nossa mensagem que esta em tratamento na classe TaskModel(em setTitle)  
         //getMostSpecificCause tira a mensagem JSON pars, pois ele esta esperando um objeto. 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }
}
