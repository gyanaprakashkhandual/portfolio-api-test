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