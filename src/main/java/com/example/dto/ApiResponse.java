package com.example.dto;

import com.example.dto.question.QuestionPaginationDTO;
import com.example.enums.AppLanguage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;

    private Integer code;

    private Boolean isError;

    private T data;

    private Integer total;

    public ApiResponse() {

    }

    public ApiResponse(String message, Integer code, Boolean isError, T data, Integer total) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = data;
        this.total = total;
    }

    public ApiResponse(Integer code, Boolean isError) {
        this.code = code;
        this.isError = isError;
        this.data = null;
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

    public ApiResponse(Integer code, Boolean isError, T data) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = data;
    }

    public ApiResponse(Integer code, Boolean isError, T data, int total) {
        this.code = code;
        this.isError = isError;
        this.data = data;
        this.total = total;
    }

    public ApiResponse(T data, Integer total) {
        this.data = data;
        this.total = total;
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<T>(200, false);
    }

    public static <T> ApiResponse<T> ok(Boolean isError, T data) {
        return new ApiResponse<T>(200, false, data);
    }

    public static <T> ApiResponse<T> ok(Boolean isError, Integer total) {
        return new ApiResponse<T>(200, false);
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

    public static <T> ApiResponse<T> ok(T data,Integer total) {
        return new ApiResponse<T>(200, false, data, total);
    }
}
