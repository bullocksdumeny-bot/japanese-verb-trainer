package com.verbtrainer.api;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler{
 @ExceptionHandler({IllegalArgumentException.class,MethodArgumentNotValidException.class})@ResponseStatus(HttpStatus.BAD_REQUEST)Map<String,String>bad(Exception e){return Map.of("error",e instanceof MethodArgumentNotValidException?"请求参数校验失败":e.getMessage());}
 @ExceptionHandler(NoSuchElementException.class)@ResponseStatus(HttpStatus.NOT_FOUND)Map<String,String>missing(Exception e){return Map.of("error",e.getMessage());}
 @ExceptionHandler(IllegalStateException.class)@ResponseStatus(HttpStatus.CONFLICT)Map<String,String>conflict(Exception e){return Map.of("error",e.getMessage());}
}
