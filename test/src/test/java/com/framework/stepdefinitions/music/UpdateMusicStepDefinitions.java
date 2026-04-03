package com.framework.stepdefinitions.music;

import com.aventstack.extentreports.Status;
import com.framework.base.BaseTest;
import com.framework.constants.MusicConstants;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.JsonUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateMusicStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(UpdateMusicStepDefinitions.class);

    private Response response;
    private Map<String, Object> musicPayload;
    private String currentMusicId;

    @Given("a music track exists in the system for update")
    public void aMusicTrackExistsInTheSystemForUpdate() {
        Map<String, Object> payload = JsonUtils.fileToMap(
                MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        Response createResponse = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post(MusicConstants.BASE_MUSIC_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .response();

        currentMusicId = createResponse.jsonPath().getString("data._id");
        Assert.assertNotNull(currentMusicId, "Music ID must not be null after creation");
        log.info("Pre-condition: Music created with ID {}", currentMusicId);
    }

    @Given("a music track exists with likes for update")
    public void aMusicTrackExistsWithLikesForUpdate() {
        aMusicTrackExistsInTheSystemForUpdate();
        Map<String, Object> likeBody = new HashMap<>();
        likeBody.put("userId", "64f1a2b3c4d5e6f7a8b9c111");
        given()
                .spec(requestSpec)
                .body(likeBody)
                .when()
                .post(MusicConstants.BASE_MUSIC_ENDPOINT + "/" + currentMusicId + "/like")
                .then()
                .extract()
                .response();
        log.info("Pre-condition: Liked music track {}", currentMusicId);
    }

    @Given("a music track exists with comments for update")
    public void aMusicTrackExistsWithCommentsForUpdate() {
        aMusicTrackExistsInTheSystemForUpdate();
        Map<String, Object> commentBody = new HashMap<>();
        commentBody.put("userId", "64f1a2b3c4d5e6f7a8b9c111");
        commentBody.put("text", "Great track!");
        given()
                .spec(requestSpec)
                .body(commentBody)
                .when()
                .post(MusicConstants.BASE_MUSIC_ENDPOINT + "/" + currentMusicId + "/comments")
                .then()
                .extract()
                .response();
        log.info("Pre-condition: Commented on music track {}", currentMusicId);
    }

    @Given("I have an update music payload with all fields")
    public void iHaveAnUpdateMusicPayloadWithAllFields() {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.UPDATE_MUSIC_JSON);
        log.info("Loaded update music payload: {}", musicPayload);
    }

    @Given("I have an update payload with only field {string} set to {string}")
    public void iHaveAnUpdatePayloadWithOnlyFieldSetToString(String field, String value) {
        musicPayload = new HashMap<>();
        musicPayload.put(field, value);
        log.info("Update payload: {} = {}", field, value);
    }

    @Given("I have an update payload with {string} set to string {string}")
    public void iHaveAnUpdatePayloadWithFieldSetToStringValue(String field, String value) {
        musicPayload = new HashMap<>();
        musicPayload.put(field, value);
        log.info("Update payload with string value: {} = {}", field, value);
    }

    @Given("I have an update payload with {string} set to {int}")
    public void iHaveAnUpdatePayloadWithIntField(String field, int value) {
        musicPayload = new HashMap<>();
        musicPayload.put(field, value);
        log.info("Update payload: {} = {}", field, value);
    }

    @Given("I have an empty update payload")
    public void iHaveAnEmptyUpdatePayload() {
        musicPayload = new HashMap<>();
        log.info("Using empty update payload");
    }

    @Given("I have a valid music payload for update")
    public void iHaveAValidMusicPayloadForUpdate() {
        musicPayload = JsonUtils.fileToMap(MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        log.info("Loaded valid music payload for update test: {}", musicPayload);
    }

    @When("I send a PUT request to {string} with the update payload")
    public void iSendAPUTRequestToWithTheUpdatePayload(String endpoint) {
        String resolvedEndpoint = resolvePathParams(endpoint);
        response = given()
                .spec(requestSpec)
                .body(musicPayload)
                .when()
                .put(resolvedEndpoint)
                .then()
                .extract()
                .response();

        log.info("PUT {} | Status: {}", resolvedEndpoint, response.getStatusCode());
        ExtentReportManager.getTest().log(Status.INFO,
                "PUT " + resolvedEndpoint + " | Status: " + response.getStatusCode());
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

    @And("the response data array field {string} should be present")
    public void theResponseDataArrayFieldShouldBePresent(String field) {
        List<?> list = response.jsonPath().getList("data." + field);
        Assert.assertNotNull(list, "data." + field + " should be present as a list");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is present");
    }

    private String resolvePathParams(String endpoint) {
        if (endpoint.contains("{musicId}") && currentMusicId != null) {
            return endpoint.replace("{musicId}", currentMusicId);
        }
        return endpoint;
    }
}