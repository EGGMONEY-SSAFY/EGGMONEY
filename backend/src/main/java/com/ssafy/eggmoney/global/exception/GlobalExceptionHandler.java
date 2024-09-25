package com.ssafy.eggmoney.global.exception;


import com.ssafy.eggmoney.global.dto.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public Mono<ResponseEntity<ResponseApi<String>>> handleRuntimeException(RuntimeException ex){
//        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ResponseApi.fail("Internal Server Error: " + ex.getMessage())));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public Mono<ResponseEntity<ResponseApi<String>>> handleException(Exception ex){
//        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ResponseApi.fail("Bad Request: " + ex.getMessage())));
//    }
}
