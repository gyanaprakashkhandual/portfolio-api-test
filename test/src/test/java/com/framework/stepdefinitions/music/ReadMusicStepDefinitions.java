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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReadMusicStepDefinitions extends BaseTest {

    private static final Logger log = LogManager.getLogger(ReadMusicStepDefinitions.class);

    private Response response;
    private String currentMusicId;

    @Given("a music track exists in the system")
    public void aMusicTrackExistsInTheSystem() {
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

    @Given("a music track exists without lyrics")
    public void aMusicTrackExistsWithoutLyrics() {
        Map<String, Object> payload = JsonUtils.fileToMap(
                MusicConstants.TEST_DATA_DIR + MusicConstants.CREATE_MUSIC_JSON);
        payload.remove("lyrics");
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
        log.info("Pre-condition: Music without lyrics created with ID {}", currentMusicId);
    }

    @Given("at least two music tracks exist in the system")
    public void atLeastTwoMusicTracksExistInTheSystem() {
        aMusicTrackExistsInTheSystem();
        aMusicTrackExistsInTheSystem();
    }

    @Given("at least one music track exists in the system")
    public void atLeastOneMusicTrackExistsInTheSystem() {
        aMusicTrackExistsInTheSystem();
    }

    @Given("the music collection is empty")
    public void theMusicCollectionIsEmpty() {
        Response allMusic = given()
                .spec(requestSpec)
                .when()
                .get(MusicConstants.BASE_MUSIC_ENDPOINT)
                .then()
                .extract()
                .response();

        List<Object> tracks = allMusic.jsonPath().getList("data");
        if (tracks != null) {
            for (Object track : tracks) {
                String id = ((Map<?, ?>) track).get("_id").toString();
                given().spec(requestSpec)
                        .when()
                        .delete(MusicConstants.BASE_MUSIC_ENDPOINT + "/" + id)
                        .then()
                        .extract()
                        .response();
            }
        }
        log.info("Pre-condition: Music collection cleared");
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
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

    @And("the response field {string} should be {int}")
    public void theResponseFieldShouldBeInt(String field, int expectedValue) {
        int actualValue = response.jsonPath().getInt(field);
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

    @And("the response should contain field {string}")
    public void theResponseShouldContainField(String field) {
        Object value = response.jsonPath().get(field);
        Assert.assertNotNull(value, "Response should contain field '" + field + "'");
        ExtentReportManager.getTest().log(Status.PASS, "Field '" + field + "' is present");
    }

    @And("the response should contain a list under {string}")
    public void theResponseShouldContainAListUnder(String field) {
        List<?> list = response.jsonPath().getList(field);
        Assert.assertNotNull(list, "Field '" + field + "' should be a list");
        ExtentReportManager.getTest().log(Status.PASS, "'" + field + "' list found with size: " + list.size());
    }

    @And("the response should contain an empty list under {string}")
    public void theResponseShouldContainAnEmptyListUnder(String field) {
        List<?> list = response.jsonPath().getList(field);
        Assert.assertNotNull(list, "Field '" + field + "' should be present");
        Assert.assertTrue(list.isEmpty(), "Field '" + field + "' should be empty but had: " + list.size());
        ExtentReportManager.getTest().log(Status.PASS, "'" + field + "' is an empty list");
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

    @And("the response data field {string} should be null or absent")
    public void theResponseDataFieldShouldBeNullOrAbsent(String field) {
        Object value = response.jsonPath().get("data." + field);
        Assert.assertNull(value, "data." + field + " should be null or absent");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is null or absent");
    }

    @And("the response data field {string} should be of type String")
    public void theResponseDataFieldShouldBeOfTypeString(String field) {
        Object value = response.jsonPath().get("data." + field);
        Assert.assertNotNull(value, "data." + field + " should not be null");
        Assert.assertTrue(value instanceof String,
                "data." + field + " should be String but was: " + value.getClass().getSimpleName());
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is String");
    }

    @And("the response data field {string} should be of type Number")
    public void theResponseDataFieldShouldBeOfTypeNumber(String field) {
        Object value = response.jsonPath().get("data." + field);
        Assert.assertNotNull(value, "data." + field + " should not be null");
        Assert.assertTrue(value instanceof Number,
                "data." + field + " should be Number but was: " + value.getClass().getSimpleName());
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is Number");
    }

    @And("the response data field {string} should be of type List")
    public void theResponseDataFieldShouldBeOfTypeList(String field) {
        List<?> list = response.jsonPath().getList("data." + field);
        Assert.assertNotNull(list, "data." + field + " should be a list");
        ExtentReportManager.getTest().log(Status.PASS, "data." + field + " is List");
    }

    @And("the response data list should be sorted by {string} descending")
    public void theResponseDataListShouldBeSortedByDescending(String field) {
        List<String> dates = response.jsonPath().getList("data." + field);
        Assert.assertNotNull(dates, "data." + field + " list should not be null");
        for (int i = 0; i < dates.size() - 1; i++) {
            Assert.assertTrue(
                    dates.get(i).compareTo(dates.get(i + 1)) >= 0,
                    "List is not sorted descending by " + field + " at index " + i);
        }
        ExtentReportManager.getTest().log(Status.PASS, "data sorted by " + field + " descending");
    }

    @And("the response field {string} should equal the size of the {string} array")
    public void theResponseFieldShouldEqualTheSizeOfTheArray(String totalField, String arrayField) {
        int total = response.jsonPath().getInt(totalField);
        List<?> list = response.jsonPath().getList(arrayField);
        Assert.assertNotNull(list, arrayField + " should not be null");
        Assert.assertEquals(total, list.size(),
                "'" + totalField + "' value " + total + " should equal size of '"
                        + arrayField + "' array " + list.size());
        ExtentReportManager.getTest().log(Status.PASS,
                totalField + " == " + arrayField + ".size() == " + total);
    }

    @And("every item in the {string} array should have fields {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void everyItemInArrayShouldHaveFields(String arrayField,
            String f1, String f2, String f3, String f4, String f5,
            String f6, String f7, String f8, String f9) {
        List<Map<String, Object>> items = response.jsonPath().getList(arrayField);
        Assert.assertNotNull(items, arrayField + " should not be null");
        List<String> requiredFields = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9);
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            for (String field : requiredFields) {
                Assert.assertTrue(item.containsKey(field),
                        "Item at index " + i + " is missing field '" + field + "'");
            }
        }
        ExtentReportManager.getTest().log(Status.PASS,
                "All items in '" + arrayField + "' contain required fields");
    }

    private String resolvePathParams(String endpoint) {
        if (endpoint.contains("{musicId}") && currentMusicId != null) {
            return endpoint.replace("{musicId}", currentMusicId);
        }
        return endpoint;
    }
}