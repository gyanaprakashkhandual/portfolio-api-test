package com.framework.utils;

import com.framework.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestBuilder {

    private final RequestSpecBuilder specBuilder;

    private RequestBuilder() {
        ConfigReader config = ConfigReader.getInstance();
        specBuilder = new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setBasePath("/" + config.getApiVersion())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", config.getAuthToken());
    }

    public static RequestBuilder create() {
        return new RequestBuilder();
    }

    public RequestBuilder withHeader(String name, String value) {
        specBuilder.addHeader(name, value);
        return this;
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        specBuilder.addHeaders(headers);
        return this;
    }

    public RequestBuilder withQueryParam(String name, Object value) {
        specBuilder.addQueryParam(name, value);
        return this;
    }

    public RequestBuilder withQueryParams(Map<String, Object> params) {
        specBuilder.addQueryParams(params);
        return this;
    }

    public RequestBuilder withBody(Object body) {
        specBuilder.setBody(body);
        return this;
    }

    public RequestBuilder withBasicAuth(String username, String password) {
        specBuilder.setAuth(
                io.restassured.RestAssured.basic(username, password));
        return this;
    }

    public RequestSpecification build() {
        return specBuilder.build();
    }
}