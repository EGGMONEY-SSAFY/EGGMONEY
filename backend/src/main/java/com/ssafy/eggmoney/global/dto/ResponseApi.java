package com.ssafy.eggmoney.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseApi<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> ResponseApi<T> success(T data){
        return new ResponseApi<>(true, data, "Success");
    }

    public static <T> ResponseApi<T> fail(String message){
        return new ResponseApi<>(false, null, message);
    }
}
