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