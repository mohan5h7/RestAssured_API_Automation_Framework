package runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepdefinitions",
                "hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/Cucumber.html",
                "json:target/cucumber-reports/Cucumber.json"
        },
        monochrome = true,
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {

	    return super.scenarios();

	}}