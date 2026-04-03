# API Testing Project Setup Guide

## RestAssured | Cucumber | TestNG | JUnit | ExtentReport

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Structure](#project-structure)
3. [Maven Dependencies](#maven-dependencies)
4. [Environment Configuration](#environment-configuration)
5. [Config Reader Utility](#config-reader-utility)
6. [Base Test Setup](#base-test-setup)
7. [Request Specification Builder](#request-specification-builder)
8. [Cucumber Feature Files](#cucumber-feature-files)
9. [Step Definitions](#step-definitions)
10. [TestNG Runner Configuration](#testng-runner-configuration)
11. [JUnit Runner Configuration](#junit-runner-configuration)
12. [ExtentReport Integration](#extentreport-integration)
13. [Utility Classes](#utility-classes)
14. [TestNG XML Suite Configuration](#testng-xml-suite-configuration)
15. [Running the Tests](#running-the-tests)
16. [CI/CD Integration](#cicd-integration)

---

## Prerequisites

Ensure the following are installed on your machine before starting:

- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.8+
- An IDE such as IntelliJ IDEA or Eclipse
- Git

Verify your installations:

```bash
java -version
mvn -version
git --version
```

---

## Project Structure

```
api-testing-framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/framework/
│   │           └── (empty or shared models if needed)
│   └── test/
│       ├── java/
│       │   └── com/framework/
│       │       ├── base/
│       │       │   └── BaseTest.java
│       │       ├── config/
│       │       │   └── ConfigReader.java
│       │       ├── constants/
│       │       │   └── EndpointConstants.java
│       │       ├── hooks/
│       │       │   └── Hooks.java
│       │       ├── pojo/
│       │       │   └── User.java
│       │       ├── reports/
│       │       │   └── ExtentReportManager.java
│       │       ├── runners/
│       │       │   ├── TestNGRunner.java
│       │       │   └── JUnitRunner.java
│       │       ├── stepdefinitions/
│       │       │   └── UserStepDefinitions.java
│       │       └── utils/
│       │           ├── RequestBuilder.java
│       │           ├── JsonUtils.java
│       │           └── LoggerUtils.java
│       └── resources/
│           ├── config/
│           │   ├── config.properties
│           │   ├── dev.properties
│           │   ├── qa.properties
│           │   └── prod.properties
│           ├── features/
│           │   └── user/
│           │       └── UserAPI.feature
│           ├── testdata/
│           │   └── user/
│           │       └── create_user.json
│           ├── cucumber.properties
│           └── testng.xml
├── reports/
│   └── extent/
├── logs/
├── pom.xml
└── README.md
```

---

## Maven Dependencies

Create a new Maven project and replace the contents of `pom.xml` with the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.framework</groupId>
    <artifactId>api-testing-framework</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <!-- Dependency Versions -->
        <restassured.version>5.4.0</restassured.version>
        <cucumber.version>7.15.0</cucumber.version>
        <testng.version>7.9.0</testng.version>
        <junit.version>4.13.2</junit.version>
        <extentreports.version>5.1.1</extentreports.version>
        <jackson.version>2.16.1</jackson.version>
        <log4j.version>2.22.1</log4j.version>
    </properties>

    <dependencies>

        <!-- RestAssured -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${restassured.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-path</artifactId>
            <version>${restassured.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>xml-path</artifactId>
            <version>${restassured.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Cucumber -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-testng</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- TestNG -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- JUnit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- ExtentReports -->
        <dependency>
            <groupId>com.aventstack</groupId>
            <artifactId>extentreports</artifactId>
            <version>${extentreports.version}</version>
        </dependency>

        <dependency>
            <groupId>tech.grasshopper</groupId>
            <artifactId>extentreports-cucumber7-adapter</artifactId>
            <version>1.14.0</version>
        </dependency>

        <!-- Jackson (JSON Serialization/Deserialization) -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <!-- Log4j2 -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <!-- Apache Commons -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.14.0</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>

            <!-- Maven Surefire Plugin for TestNG -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <systemPropertyVariables>
                        <env>${env}</env>
                    </systemPropertyVariables>
                </configuration>
            </plugin>

            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.12.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>

        </plugins>

        <testResources>
            <testResource>
                <directory>src/test/resources</directory>
            </testResource>
        </testResources>
    </build>

</project>
```

---

## Environment Configuration

The framework supports multiple environments: `dev`, `qa`, and `prod`. Environment selection is controlled via a Maven system property at runtime.

### `src/test/resources/config/config.properties`

This is the base configuration file. Environment-specific files override these values.

```properties
# Base Configuration
app.name=API Testing Framework
default.env=qa
request.timeout=30000
connection.timeout=10000
log.level=INFO
report.path=reports/extent/
```

### `src/test/resources/config/dev.properties`

```properties
base.url=https://dev-api.yourapp.com
api.version=v1
auth.token=Bearer dev_token_here
username=dev_user
password=dev_pass123
```

### `src/test/resources/config/qa.properties`

```properties
base.url=https://qa-api.yourapp.com
api.version=v1
auth.token=Bearer qa_token_here
username=qa_user
password=qa_pass123
```

### `src/test/resources/config/prod.properties`

```properties
base.url=https://api.yourapp.com
api.version=v1
auth.token=Bearer prod_token_here
username=prod_user
password=prod_pass123
```

### `src/test/resources/cucumber.properties`

```properties
cucumber.publish.quiet=true
```

---

## Config Reader Utility

### `src/test/java/com/framework/config/ConfigReader.java`

```java
package com.framework.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        String env = System.getProperty("env", "qa");
        loadProperties("config/config.properties");
        loadProperties("config/" + env + ".properties");
        log.info("Configuration loaded for environment: {}", env);
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    private void loadProperties(String filePath) {
        try (FileInputStream fis = new FileInputStream(
                "src/test/resources/" + filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            log.error("Failed to load properties file: {}", filePath, e);
            throw new RuntimeException("Could not load config: " + filePath, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key not found: " + key);
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public String getApiVersion() {
        return get("api.version");
    }

    public String getAuthToken() {
        return get("auth.token");
    }
}
```

---

## Base Test Setup

### `src/test/java/com/framework/base/BaseTest.java`

```java
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
```

---

## Request Specification Builder

### `src/test/java/com/framework/utils/RequestBuilder.java`

```java
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
```

---

## Endpoint Constants

### `src/test/java/com/framework/constants/EndpointConstants.java`

```java
package com.framework.constants;

public class EndpointConstants {

    private EndpointConstants() {}

    // User Endpoints
    public static final String USERS            = "/users";
    public static final String USER_BY_ID       = "/users/{userId}";
    public static final String USER_POSTS       = "/users/{userId}/posts";

    // Auth Endpoints
    public static final String AUTH_LOGIN       = "/auth/login";
    public static final String AUTH_LOGOUT      = "/auth/logout";
    public static final String AUTH_REFRESH     = "/auth/refresh";

    // Product Endpoints
    public static final String PRODUCTS         = "/products";
    public static final String PRODUCT_BY_ID    = "/products/{productId}";

    // Health Check
    public static final String HEALTH           = "/health";
}
```

---

## POJO / Model Class

### `src/test/java/com/framework/pojo/User.java`

```java
package com.framework.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    // Constructors
    public User() {}

    public User(String name, String email, String role) {
        this.name  = name;
        this.email = email;
        this.role  = role;
    }

    // Getters and Setters
    public Integer getId()            { return id; }
    public void setId(Integer id)     { this.id = id; }

    public String getName()           { return name; }
    public void setName(String name)  { this.name = name; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    public String getRole()               { return role; }
    public void setRole(String role)      { this.role = role; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', role='" + role + "'}";
    }
}
```

---

## JSON Utility

### `src/test/java/com/framework/utils/JsonUtils.java`

```java
package com.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static final Logger log        = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String TEST_DATA   = "src/test/resources/testdata/";

    private JsonUtils() {}

    public static <T> T fromFile(String relativePath, Class<T> type) {
        File file = new File(TEST_DATA + relativePath);
        try {
            return mapper.readValue(file, type);
        } catch (IOException e) {
            log.error("Failed to deserialize file: {}", relativePath, e);
            throw new RuntimeException("JSON read error: " + relativePath, e);
        }
    }

    public static Map<String, Object> fileToMap(String relativePath) {
        File file = new File(TEST_DATA + relativePath);
        try {
            return mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            log.error("Failed to parse file to map: {}", relativePath, e);
            throw new RuntimeException("JSON parse error: " + relativePath, e);
        }
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Failed to serialize object: {}", object, e);
            throw new RuntimeException("JSON write error", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON string", e);
            throw new RuntimeException("JSON parse error", e);
        }
    }
}
```

---

## ExtentReport Integration

### `src/test/java/com/framework/reports/ExtentReportManager.java`

```java
package com.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.config.ConfigReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private ExtentReportManager() {}

    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentReportManager.class) {
                if (extent == null) {
                    initReport();
                }
            }
        }
        return extent;
    }

    private static void initReport() {
        String timestamp    = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath   = ConfigReader.getInstance()
                .get("report.path", "reports/extent/")
                + "TestReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("API Test Report");
        spark.config().setReportName("API Automation Results");
        spark.config().setEncoding("UTF-8");
        spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment",
                System.getProperty("env", "qa").toUpperCase());
        extent.setSystemInfo("Base URL",
                ConfigReader.getInstance().getBaseUrl());
        extent.setSystemInfo("Executed By",
                System.getProperty("user.name"));
        extent.setSystemInfo("Framework",
                "RestAssured + Cucumber + TestNG");
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
```

---

## Cucumber Hooks

### `src/test/java/com/framework/hooks/Hooks.java`

```java
package com.framework.hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.framework.reports.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Starting Scenario: {}", scenario.getName());
        ExtentTest extentTest = ExtentReportManager.getInstance()
                .createTest(scenario.getName());
        extentTest.assignCategory(
                scenario.getSourceTagNames().toArray(new String[0]));
        ExtentReportManager.setTest(extentTest);
    }

    @After
    public void afterScenario(Scenario scenario) {
        ExtentTest extentTest = ExtentReportManager.getTest();
        if (scenario.isFailed()) {
            extentTest.log(Status.FAIL,
                    "Scenario Failed: " + scenario.getName());
            log.error("Scenario FAILED: {}", scenario.getName());
        } else {
            extentTest.log(Status.PASS,
                    "Scenario Passed: " + scenario.getName());
            log.info("Scenario PASSED: {}", scenario.getName());
        }
    }

    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flushReport();
        log.info("ExtentReport flushed successfully.");
    }
}
```

---

## Cucumber Feature Files

### `src/test/resources/features/user/UserAPI.feature`

```gherkin
@UserAPI @Regression
Feature: User API - CRUD Operations

  Background:
    Given the API base URL is configured

  @Smoke @GetUser
  Scenario: Get all users successfully
    When I send a GET request to "/users"
    Then the response status code should be 200
    And the response body should contain a list of users

  @Smoke @CreateUser
  Scenario: Create a new user with valid data
    Given I have the following user details
      | name        | email                | role  |
      | John Smith  | john.smith@test.com  | admin |
    When I send a POST request to "/users" with the user body
    Then the response status code should be 201
    And the response body should contain "name" as "John Smith"
    And the response body should contain "email" as "john.smith@test.com"

  @Regression @GetUser
  Scenario Outline: Get user by ID
    When I send a GET request to "/users/<userId>"
    Then the response status code should be <statusCode>

    Examples:
      | userId | statusCode |
      | 1      | 200        |
      | 9999   | 404        |

  @Regression @UpdateUser
  Scenario: Update an existing user
    Given a user exists with ID 1
    When I send a PUT request to "/users/1" with updated name "Jane Doe"
    Then the response status code should be 200
    And the response body should contain "name" as "Jane Doe"

  @Regression @DeleteUser
  Scenario: Delete an existing user
    Given a user exists with ID 1
    When I send a DELETE request to "/users/1"
    Then the response status code should be 204
```

---

## Step Definitions

### `src/test/java/com/framework/stepdefinitions/UserStepDefinitions.java`

```java
package com.framework.stepdefinitions;

import com.aventstack.extentreports.Status;
import com.framework.base.BaseTest;
import com.framework.constants.EndpointConstants;
import com.framework.pojo.User;
import com.framework.reports.ExtentReportManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(UserStepDefinitions.class);

    private Response response;
    private User     userPayload;

    @Given("the API base URL is configured")
    public void theAPIBaseURLIsConfigured() {
        log.info("API Base URL: {}", config.getBaseUrl());
        ExtentReportManager.getTest()
                .log(Status.INFO, "Base URL: " + config.getBaseUrl());
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        response = given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        log.info("GET {} | Status: {}", endpoint, response.getStatusCode());
        ExtentReportManager.getTest()
                .log(Status.INFO, "GET " + endpoint + " | Status: " + response.getStatusCode());
    }

    @Given("I have the following user details")
    public void iHaveTheFollowingUserDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data       = rows.get(0);
        userPayload = new User(data.get("name"), data.get("email"), data.get("role"));
        log.info("User payload built: {}", userPayload);
    }

    @When("I send a POST request to {string} with the user body")
    public void iSendAPOSTRequestToWithTheUserBody(String endpoint) {
        response = given()
                .spec(requestSpec)
                .body(userPayload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        log.info("POST {} | Status: {}", endpoint, response.getStatusCode());
        ExtentReportManager.getTest()
                .log(Status.INFO, "POST " + endpoint + " | Status: " + response.getStatusCode());
    }

    @Given("a user exists with ID {int}")
    public void aUserExistsWithID(int userId) {
        response = given()
                .spec(requestSpec)
                .pathParam("userId", userId)
                .when()
                .get(EndpointConstants.USER_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .response();

        log.info("Pre-condition: User with ID {} exists.", userId);
    }

    @When("I send a PUT request to {string} with updated name {string}")
    public void iSendAPUTRequestToWithUpdatedName(String endpoint, String name) {
        User updatePayload = new User();
        updatePayload.setName(name);

        response = given()
                .spec(requestSpec)
                .body(updatePayload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();

        log.info("PUT {} | Status: {}", endpoint, response.getStatusCode());
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        response = given()
                .spec(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();

        log.info("DELETE {} | Status: {}", endpoint, response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch.");
        ExtentReportManager.getTest()
                .log(Status.PASS, "Status code verified: " + actualStatusCode);
    }

    @And("the response body should contain a list of users")
    public void theResponseBodyShouldContainAListOfUsers() {
        List<Object> users = response.jsonPath().getList("$");
        Assert.assertFalse(users.isEmpty(), "User list should not be empty.");
        ExtentReportManager.getTest()
                .log(Status.PASS, "User list returned with " + users.size() + " entries.");
    }

    @And("the response body should contain {string} as {string}")
    public void theResponseBodyShouldContain(String field, String expectedValue) {
        String actualValue = response.jsonPath().getString(field);
        Assert.assertEquals(actualValue, expectedValue,
                "Field '" + field + "' mismatch.");
        ExtentReportManager.getTest()
                .log(Status.PASS, "Field '" + field + "' = '" + actualValue + "'");
    }
}
```

---

## TestNG Runner Configuration

### `src/test/java/com/framework/runners/TestNGRunner.java`

```java
package com.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features   = "src/test/resources/features",
        glue       = {"com.framework.stepdefinitions", "com.framework.hooks"},
        tags       = "@Regression",
        plugin     = {
                "pretty",
                "html:reports/cucumber/html/report.html",
                "json:reports/cucumber/json/report.json",
                "junit:reports/cucumber/xml/report.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun     = false
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

---

## JUnit Runner Configuration

### `src/test/java/com/framework/runners/JUnitRunner.java`

```java
package com.framework.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features   = "src/test/resources/features",
        glue       = {"com.framework.stepdefinitions", "com.framework.hooks"},
        tags       = "@Smoke",
        plugin     = {
                "pretty",
                "html:reports/cucumber/html/junit-report.html",
                "json:reports/cucumber/json/junit-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun     = false
)
public class JUnitRunner {
    // JUnit entry point — no body required
}
```

---

## TestNG XML Suite Configuration

### `src/test/resources/testng.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">

<suite name="API Test Suite" verbose="1" thread-count="1" parallel="none">

    <parameter name="env" value="qa"/>

    <listeners>
        <listener class-name="org.testng.reporters.JUnitReportReporter"/>
    </listeners>

    <test name="Regression Tests">
        <parameter name="tags" value="@Regression"/>
        <classes>
            <class name="com.framework.runners.TestNGRunner"/>
        </classes>
    </test>

    <test name="Smoke Tests">
        <parameter name="tags" value="@Smoke"/>
        <classes>
            <class name="com.framework.runners.TestNGRunner"/>
        </classes>
    </test>

</suite>
```

---

## Test Data File

### `src/test/resources/testdata/user/create_user.json`

```json
{
  "name": "John Smith",
  "email": "john.smith@test.com",
  "role": "admin"
}
```

---

## Running the Tests

### Run all tests via Maven (default QA environment)

```bash
mvn clean test
```

### Run tests against a specific environment

```bash
mvn clean test -Denv=dev
mvn clean test -Denv=qa
mvn clean test -Denv=prod
```

### Run tests by tag

```bash
mvn clean test -Dcucumber.filter.tags="@Smoke"
mvn clean test -Dcucumber.filter.tags="@Regression"
mvn clean test -Dcucumber.filter.tags="@UserAPI and @Smoke"
mvn clean test -Dcucumber.filter.tags="not @WIP"
```

### Run only the JUnit runner

```bash
mvn clean test -Dtest=JUnitRunner
```

### Run with a specific TestNG XML

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

---

## CI/CD Integration

### GitHub Actions — `.github/workflows/api-tests.yml`

```yaml
name: API Test Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]
  schedule:
    - cron: "0 6 * * *" # Daily at 06:00 UTC

jobs:
  api-tests:
    runs-on: ubuntu-latest

    strategy:
      matrix:
        env: [dev, qa]

    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Set Up JDK 11
        uses: actions/setup-java@v4
        with:
          java-version: "11"
          distribution: "temurin"

      - name: Cache Maven Dependencies
        uses: actions/cache@v4
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-maven-

      - name: Run API Tests (${{ matrix.env }})
        run: mvn clean test -Denv=${{ matrix.env }} -Dcucumber.filter.tags="@Regression"
        env:
          AUTH_TOKEN: ${{ secrets.AUTH_TOKEN }}

      - name: Upload Test Reports
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: test-reports-${{ matrix.env }}
          path: |
            reports/
            target/surefire-reports/
          retention-days: 7
```

---

## Key Notes

**Environment Switching** — Pass `-Denv=dev|qa|prod` to Maven at runtime. The `ConfigReader` automatically loads the correct properties file without any code change.

**Parallel Execution** — To enable parallel scenario execution in TestNG, set `parallel="methods"` and increase `thread-count` in `testng.xml`. Ensure `ThreadLocal` is used in any shared state (as done in `ExtentReportManager`).

**Tagging Strategy** — Use `@Smoke` for critical path tests, `@Regression` for full suite runs, and `@WIP` to exclude in-progress scenarios from pipeline runs.

**Adding a New API Module** — Create a new feature file under `features/`, a corresponding step definition class under `stepdefinitions/`, and add endpoint constants to `EndpointConstants.java`. No runner changes are needed.

**Report Location** — After each run, the ExtentReport HTML file is generated under `reports/extent/` with a timestamp in the filename. Open it in any browser.
