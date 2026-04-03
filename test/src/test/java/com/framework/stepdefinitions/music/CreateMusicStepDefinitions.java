package com.framework.stepdefinitions.music;

import com.aventstack.extentreports.Status;
import com.framework.base.BaseTest;
import com.framework.constants.MusicConstants;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.JsonUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateMusicStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(CreateMusicStepDefinitions.class);

    Response response;
    Map<String, Object> musicPayload;
    String currentMusicId;

    @Given("the music API base URL is configured")
    public void theMusicAPIBaseURLIsConfigured() {
        log.info("Music API base URL: {}", config.getBaseUrl());
        ExtentReportManager.getTest().log(Status.INFO, "Base URL: " + config.getBaseUrl());
    }

    @Given("I have a valid music payload")
    public void iHaveAValidMusicPayload() {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        log.info("Loaded valid music payload: {}", musicPayload);
    }

    @Given("I have a valid music payload with lyrics")
    public void iHaveAValidMusicPayloadWithLyrics() {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_WITH_LYRICS);
        log.info("Loaded music payload with lyrics: {}", musicPayload);
    }

    @Given("I have a music payload missing the {string} field")
    public void iHaveAMusicPayloadMissingTheField(String field) {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        musicPayload.remove(field);
        log.info("Removed field '{}' from payload", field);
    }

    @Given("I have an empty music payload")
    public void iHaveAnEmptyMusicPayload() {
        musicPayload = new HashMap<>();
        log.info("Using empty music payload");
    }

    @Given("I have a music payload with {string} set to {string}")
    public void iHaveAMusicPayloadWithStringFieldSetToStringValue(String field, String value) {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        musicPayload.put(field, value);
        log.info("Set field '{}' to '{}'", field, value);
    }

    @Given("I have a music payload with {string} set to {int}")
    public void iHaveAMusicPayloadWithIntFieldSetToValue(String field, int value) {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        musicPayload.put(field, value);
        log.info("Set field '{}' to {}", field, value);
    }

    @Given("I have a music payload with same title but different artist")
    public void iHaveAMusicPayloadWithSameTitleButDifferentArtist() {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        musicPayload.put("artist", "Alternative Artist");
        log.info("Set different artist for duplicate title test");
    }

    @When("I send a POST request to {string} with the music payload")
    public void iSendAPOSTRequestToWithTheMusicPayload(String endpoint) {
        response = given()
                .spec(requestSpec)
                .body(musicPayload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        String createdId = response.jsonPath().getString("data._id");
        if (createdId != null) currentMusicId = createdId;

        log.info("POST {} | Status: {}", endpoint, response.getStatusCode());
        ExtentReportManager.getTest().log(Status.INFO,
                "POST " + endpoint + " | Status: " + response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Response body: " + response.getBody().asString());
        ExtentReportManager.getTest().log(Status.PASS, "Status code verified: " + actualStatusCode);
    }

    @And("the response field {string} should be true")
    public void theResponseFieldShouldBeTrue(String field) {
        Boolean value = response.jsonPath().getBoolean(field);
        Assert.assertTrue(value, "Expected field '" + field + "' to be true but was: " + value);
        ExtentReportManager.getTest().log(Status.PASS, field + " = true");
    }

    @And("the response field {string} should be false")
    public void theResponseFieldShouldBeFalse(String field) {
        Boolean value = response.jsonPath().getBoolean(field);
        Assert.assertFalse(value, "Expected field '" + field + "' to be false but was: " + value);
        ExtentReportManager.getTest().log(Status.PASS, field + " = false");
    }

    @And("the response field {string} should be {string}")
    public void theResponseFieldShouldBeString(String field, String expectedValue) {
        String actualValue = response.jsonPath().getString(field);
        Assert.assertEquals(actualValue, expectedValue, "Field '" + field + "' mismatch.");
        ExtentReportManager.getTest().log(Status.PASS, field + " = " + actualValue);
    }

    @And("the response field {string} should not be empty")
    public void theResponseFieldShouldNotBeEmpty(String field) {
        String value = response.jsonPath().getString(field);
        Assert.assertNotNull(value, "Field '" + field + "' should not be null");
        Assert.assertFalse(value.trim().isEmpty(), "Field '" + field + "' should not be empty");
        ExtentReportManager.getTest().log(Status.PASS, field + " is not empty: " + value);
    }

    @And("the response data should contain field {string} with value {string}")
    public void theResponseDataShouldContainFieldWithStringValue(String field, String expectedValue) {
        String actualValue = response.jsonPath().getString("data." + field);
        Assert.assertEquals(actualValue, expectedValue, "data." + field + " mismatch.");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " = " + actualValue);
    }

    @And("the response data should contain field {string} with value {int}")
    public void theResponseDataShouldContainFieldWithIntValue(String field, int expectedValue) {
        int actualValue = response.jsonPath().getInt("data." + field);
        Assert.assertEquals(actualValue, expectedValue, "data." + field + " mismatch.");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " = " + actualValue);
    }

    @And("the response data should have field {string} present")
    public void theResponseDataShouldHaveFieldPresent(String field) {
        Object value = response.jsonPath().get("data." + field);
        Assert.assertNotNull(value, "data." + field + " should be present");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is present");
    }

    @And("the response data should not have field {string}")
    public void theResponseDataShouldNotHaveField(String field) {
        Object value = response.jsonPath().get("data." + field);
        Assert.assertNull(value, "data." + field + " should not be present but was: " + value);
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is absent as expected");
    }

    @And("the response data array field {string} should be empty")
    public void theResponseDataArrayFieldShouldBeEmpty(String field) {
        List<?> list = response.jsonPath().getList("data." + field);
        Assert.assertNotNull(list, "data." + field + " should be a list");
        Assert.assertTrue(list.isEmpty(), "data." + field + " should be empty");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is empty");
    }
}