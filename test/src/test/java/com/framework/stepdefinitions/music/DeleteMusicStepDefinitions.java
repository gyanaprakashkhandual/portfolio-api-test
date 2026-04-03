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

public class DeleteMusicStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(DeleteMusicStepDefinitions.class);

    private Response response;
    private String currentMusicId;
    private int recordedTotalCount;

    @Given("a music track exists in the system for delete")
    public void aMusicTrackExistsInTheSystemForDelete() {
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

    @Given("a music track exists with likes for delete")
    public void aMusicTrackExistsWithLikesForDelete() {
        aMusicTrackExistsInTheSystemForDelete();
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

    @Given("a music track exists with comments for delete")
    public void aMusicTrackExistsWithCommentsForDelete() {
        aMusicTrackExistsInTheSystemForDelete();
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

    @Given("the total music count is recorded")
    public void theTotalMusicCountIsRecorded() {
        Response allMusic = given()
                .spec(requestSpec)
                .when()
                .get(MusicConstants.BASE_MUSIC_ENDPOINT)
                .then()
                .extract()
                .response();
        recordedTotalCount = allMusic.jsonPath().getInt("total");
        log.info("Recorded total count: {}", recordedTotalCount);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        String resolvedEndpoint = resolvePathParams(endpoint);
        response = given()
                .spec(requestSpec)
                .when()
                .delete(resolvedEndpoint)
                .then()
                .extract()
                .response();

        log.info("DELETE {} | Status: {}", resolvedEndpoint, response.getStatusCode());
        ExtentReportManager.getTest().log(Status.INFO,
                "DELETE " + resolvedEndpoint + " | Status: " + response.getStatusCode());
    }

    @When("I send a GET request to {string} for delete verification")
    public void iSendAGETRequestForDeleteVerification(String endpoint) {
        String resolvedEndpoint = resolvePathParams(endpoint);
        response = given()
                .spec(requestSpec)
                .when()
                .get(resolvedEndpoint)
                .then()
                .extract()
                .response();

        log.info("GET {} | Status: {}", resolvedEndpoint, response.getStatusCode());
        ExtentReportManager.getTest().log(Status.INFO,
                "GET " + resolvedEndpoint + " | Status: " + response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Response body: " + response.getBody().asString());
        ExtentReportManager.getTest().log(Status.PASS, "Status code verified: " + actualStatusCode);
    }

    @Then("the response status code should be in {listOfInt}")
    public void theResponseStatusCodeShouldBeIn(List<Integer> expectedCodes) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertTrue(expectedCodes.contains(actualStatusCode),
                "Expected one of " + expectedCodes + " but got " + actualStatusCode);
        ExtentReportManager.getTest().log(Status.PASS,
                "Status code " + actualStatusCode + " is in expected list " + expectedCodes);
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

    @And("the response field {string} should not be null")
    public void theResponseFieldShouldNotBeNull(String field) {
        Object value = response.jsonPath().get(field);
        Assert.assertNotNull(value, "Response field '" + field + "' should not be null");
        ExtentReportManager.getTest().log(Status.PASS, "'" + field + "' is not null");
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

    @And("the response field {string} should be decremented by {int}")
    public void theResponseFieldShouldBeDecrementedBy(String field, int decrementBy) {
        int currentTotal = response.jsonPath().getInt(field);
        Assert.assertEquals(currentTotal, recordedTotalCount - decrementBy,
                "Expected total to be " + (recordedTotalCount - decrementBy) + " but was " + currentTotal);
        ExtentReportManager.getTest().log(Status.PASS,
                "Total decremented from " + recordedTotalCount + " to " + currentTotal);
    }

    private String resolvePathParams(String endpoint) {
        if (endpoint.contains("{musicId}") && currentMusicId != null) {
            return endpoint.replace("{musicId}", currentMusicId);
        }
        return endpoint;
    }
}