package hooks;

import base.BaseTest;
import config.ConfigManager;
import context.ApiContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {

        ConfigManager.loadProperties();

        BaseTest.initializeFramework();

        ExtentTestManager.startTest(
                scenario.getName(),
                "Verify " + scenario.getName() + " functionality");

        ExtentTestManager.info(
                "Scenario Started : " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {

            ExtentTestManager.fail("Scenario Failed");

        } else {

            ExtentTestManager.pass("Scenario Passed");
        }

        // Clear thread-local data
        ApiContext.clear();

        // Write report
        ExtentManager.flush();

        BaseTest.cleanUpFramework();

        ExtentTestManager.endTest();
    }
}