package com.earthquake.tracker.quaker.mvp.model;

public class ApiResponse<T> {

    private final int statusCode;
    private T responseObject;

    public ApiResponse(final int statusCode, final T responseObject) {
        this.statusCode = statusCode;
        this.responseObject = responseObject;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getResponseObject() {
        return responseObject;
    }
}
