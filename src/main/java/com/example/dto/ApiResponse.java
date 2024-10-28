package com.example.dto;

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

        public ApiResponse(Integer code, Boolean isError) {
            this.code = code;
            this.isError = isError;
            this.data = null;
            this.total = 0;
        }

        public ApiResponse(String message, Integer code, Boolean isError) {
            this.message = message;
            this.code = code;
            this.isError = isError;
            this.data = null;
            this.total = 0;
        }

        public ApiResponse(String message, Integer code) {
            this.message = message;
            this.code = code;
            this.data = null;
            this.total = (data != null) ? data.size() : 0;
        }

        public ApiResponse(Integer code, Boolean isError, List<T> data) {
            this.code = code;
            this.isError = isError;
            this.data = data;
            this.total=(data != null) ? data.size() : 0;
        }

        public ApiResponse(String message, Integer code, Boolean isError, List<T> data, Integer total) {
            this.message = message;
            this.code = code;
            this.isError = isError;
            this.data = data;
            this.total = total;
        }

        public static <T> ApiResponse<T> ok(List<T> data) {
            return new ApiResponse<T>(200, false, data);
        }

        public static <T> ApiResponse<T> ok(Boolean isError, List<T> data) {
            return new ApiResponse<T>(200, false, data);
        }

        public static <T> ApiResponse<T> ok() {
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


    }
