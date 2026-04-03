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