package com.checkrise.todos.testing;

// This class holds data (status code, response body)
// sent by API in response to given requests
public class ApiResponse {
    private final int status;
    private final String body;

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
