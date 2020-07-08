package org.learning.restclient.exception;

import org.springframework.http.HttpStatus;

public class RestServiceException extends Exception {

    private static final long serialVersionUID = -8653095517072001035L;
    
    private HttpStatus statusCode;
    
    private String statusText;
    
    private String responseBody;
    
    public RestServiceException(HttpStatus statusCode, String statusText, String responseBody) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.responseBody = responseBody;
    }
    
    public RestServiceException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
