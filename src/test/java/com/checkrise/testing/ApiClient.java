package com.checkrise.testing;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Class is used to act as API's client during testing
public class ApiClient {
    private String server;

    public ApiClient(String server) {
        this.server = server;
    }

    // Method for making request to API without request body
    public ApiResponse request(String method, String uri){
        return request(method, uri, null);
    }

    // Method for making request to API with request body
    public ApiResponse request(String method, String uri, String requestBody) {
        try {
            // create URL the request will try to connect
            URL url = new URL(server + uri);
            // open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // set request method
            connection.setRequestMethod(method);
            // set content type header to json
            connection.setRequestProperty("Content-Type", "application/json");
            if (requestBody != null) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(requestBody.getBytes("UTF-8"));
                }
            }

            // Connect with API and process response based on status code
            connection.connect();
            InputStream inputStream = connection.getResponseCode() < 400 ?
                    connection.getInputStream() :
                    connection.getErrorStream();
            String body = IOUtils.toString(inputStream);
            // Return object containing data about API response
            return new ApiResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Connection error");
        }
    }
}
