package com.framework.stepdefinitions;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

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
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class UserStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(UserStepDefinitions.class);

    public static Logger getLog() {
        return log;
    }

    private Response response;
    private User userPayload;

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
        Map<String, String> data = rows.get(0);
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