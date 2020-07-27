package com.xiaxinyu.restful.client.spring;

import java.nio.charset.Charset;

import com.xiaxinyu.restful.client.exception.RestServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * @author XIAXINYU3
 * @date 2020.7.20
 */
@Component
public class RestClient {
    protected final Logger logger = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    RestTemplate rest;

    public <T> T get(String url, Class<T> clazz) throws RestServiceException {
        return get(url, null, clazz);
    }

    public <T> T get(String url, HttpHeaders headers, Class<T> clazz) throws RestServiceException {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, clazz);
        } catch (HttpStatusCodeException e) {
            logger.error("Response status: {}, Response body: {}", e.getMessage(), e.getResponseBodyAsString());
            throw new RestServiceException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Rest service exception {}", e.getMessage());
            throw new RestServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity.getBody();
    }

    public <T> T post(String url, Object request, Class<T> clazz) throws RestServiceException {
        return post(url, null, request, clazz);
    }

    public <T> T post(String url, HttpHeaders headers, Object request, Class<T> clazz)
            throws RestServiceException {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, clazz);
        } catch (HttpStatusCodeException e) {
            logger.error("Response status: {}, Response body: {}", e.getMessage(), e.getResponseBodyAsString());
            throw new RestServiceException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Rest service exception {}", e.getMessage());
            throw new RestServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity.getBody();
    }

    public <T> ResponseEntity<T> postForEntity(String url, HttpHeaders headers, Object request, Class<T> clazz)
            throws RestServiceException {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, clazz);
        } catch (HttpStatusCodeException e) {
            logger.error("Response status: {}, Response body: {}", e.getMessage(), e.getResponseBodyAsString());
            throw new RestServiceException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Rest service exception {}", e.getMessage());
            throw new RestServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public <T> T put(String url, Object request, Class<T> clazz) throws RestServiceException {
        return put(url, null, request, clazz);
    }

    public <T> T put(String url, HttpHeaders headers, Object request, Class<T> clazz)
            throws RestServiceException {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = rest.exchange(url, HttpMethod.PUT, requestEntity, clazz);
        } catch (HttpStatusCodeException e) {
            logger.error("Response status: {}, Response body: {}", e.getMessage(), e.getResponseBodyAsString());
            throw new RestServiceException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Rest service exception {}", e.getMessage());
            throw new RestServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity.getBody();
    }

    public <T> T delete(String url, Class<T> clazz) throws RestServiceException {
        HttpEntity<String> requestEntity = new HttpEntity<String>("");
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = rest.exchange(url, HttpMethod.DELETE, requestEntity, clazz);
        } catch (HttpStatusCodeException e) {
            logger.error("Response status: {}, Response body: {}", e.getMessage(), e.getResponseBodyAsString());
            throw new RestServiceException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Rest service exception {}", e.getMessage());
            throw new RestServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity.getBody();
    }

}
