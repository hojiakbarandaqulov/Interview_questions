package com.example.dto;

import com.example.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;

    private Integer code;

    private Boolean isError;

    private List<T> data;

    private Integer total;

    public ApiResponse() {

    }

    public ApiResponse(Integer code, Boolean isError, int total) {
        this.code = code;
        this.isError = isError;
        this.data = null;
//        this.total = total;
    }

    public ApiResponse(String message, Integer code, Boolean isError) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = null;
    }

    public ApiResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
        this.data = null;
    }

    public ApiResponse(Integer code, Boolean isError, List<T> data) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = data;
    }
    public ApiResponse(Integer code, Boolean isError, List<T> data, int total) {
        this.code = code;
        this.isError = isError;
        this.data = data;
        this.total = total;
    }


    public static <T> ApiResponse<T> ok(List<T> data, int total) {
        return new ApiResponse<T>(200, false, data,total);
    }

    public static <T> ApiResponse<T> ok(Boolean isError, List<T> data, int total) {
        return new ApiResponse<T>(200, false, data, total);
    }

    public static <T> ApiResponse<T> bad(String message) {
        return new ApiResponse<T>(message, 400, true);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<T>(message, 403, true);
    }

    public static <T> ApiResponse<T> unAuthorized(String message) {
        return new ApiResponse<T>(message, 401, true);
    }


}
