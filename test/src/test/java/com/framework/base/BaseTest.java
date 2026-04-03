package com.framework.base;

import com.framework.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    protected static ConfigReader config;

    @BeforeSuite(alwaysRun = true)
    public void initializeFramework() {
        config = ConfigReader.getInstance();

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setBasePath("/" + config.getApiVersion())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", config.getAuthToken())
                .addHeader("Accept", ContentType.JSON.toString())
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        log.info("Framework initialized. Base URI: {}", config.getBaseUrl());
    }
}