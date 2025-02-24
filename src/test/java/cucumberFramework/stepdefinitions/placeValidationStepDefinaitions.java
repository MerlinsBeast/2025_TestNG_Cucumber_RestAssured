package cucumberFramework.stepdefinitions;

import APITesting.POJOClasses.AddPlaces;
import APITesting.POJOClasses.Location;
import Utilities.CommonUtilities;
import cucumberFramework.resources.APIResourcesURIs;
import cucumberFramework.resources.TestDataBuild;
import cucumberFramework.resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class placeValidationStepDefinaitions extends Utils {

    static RequestSpecification request;
    static ResponseSpecification responseSpecification;
    static Response response;
    TestDataBuild dataBuild= new TestDataBuild();
    static String place_id_received;
    String addPlaceResponseString;
    String getPlaceResponseString;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String Language, String Address) throws IOException {
        request= given().
                spec(requestSpecifications())
                .body(dataBuild.addPlacePayload(name,Language,Address));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String apiToHit, String methodToExecute) {
        responseSpecification= new ResponseSpecBuilder()
                .expectContentType("application/json")
                .expectStatusCode(200).build();
        if(methodToExecute.equalsIgnoreCase("post")){response= request.when().post(APIResourcesURIs.valueOf(apiToHit).getResource());}
        else if (methodToExecute.equalsIgnoreCase("get")) {response= request.when().get(APIResourcesURIs.valueOf(apiToHit).getResource());}
        else if (methodToExecute.equalsIgnoreCase("delete")) {response= request.when().delete(APIResourcesURIs.valueOf(apiToHit).getResource());}

    }
    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        response.then().spec(responseSpecification)
                .body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response();
        Assert.assertTrue(response.getStatusCode()==200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        addPlaceResponseString= response.asString();
        System.out.println("Complete altered response  \n"+ addPlaceResponseString);
        Assert.assertEquals(CommonUtilities.getRawToJson(addPlaceResponseString,key),value);
    }
    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String extractedName, String apiToHit) throws IOException {
        place_id_received= CommonUtilities.getRawToJson(addPlaceResponseString,"place_id");
        System.out.println(""+place_id_received);
        request= given().
                spec(requestSpecifications())
                .queryParam("place_id",place_id_received);
        user_calls_with_http_request(apiToHit,"get");
        getPlaceResponseString= response.asString();
        String actualNameValue= CommonUtilities.getRawToJson(getPlaceResponseString,"name");
        System.out.println("extrcated  from fatures=  "+extractedName);
        System.out.println("extrcated  from api=  "+actualNameValue);
        Assert.assertTrue(extractedName.equals(actualNameValue));
    }

    @Given("Delete place Payload")
    public void delete_place_payload() throws IOException {
        request=given().spec(requestSpecifications())
               .body(dataBuild.deletePlacePayload(place_id_received));
    }
    @Then("the API call get sucess with status code {int}")
    public void the_api_call_get_sucess_with_status_code(Integer statusCode) {
        Assert.assertTrue(response.getStatusCode()==statusCode);
    }

}
