package cucumberFramework.cucumberOptions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/cucumberFramework/features/placeValidations.feature",
        plugin ="json:/target/jsonReports/cucumber-reports.json",
        glue = {"cucumberFramework/stepdefinitions"},
        tags= "@DeletePlace"
)
public class TestRunner {

}
