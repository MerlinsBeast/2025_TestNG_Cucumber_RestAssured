package cucumberFramework.stepdefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        placeValidationStepDefinaitions placeValidationStepDefinaitions = new placeValidationStepDefinaitions();
        if(cucumberFramework.stepdefinitions.placeValidationStepDefinaitions.place_id_received==null){
            placeValidationStepDefinaitions.add_place_payload_with("Vijay","German","Hinjewadi");
            placeValidationStepDefinaitions.user_calls_with_http_request("AddPlaceAPI","post");
            placeValidationStepDefinaitions.in_response_body_is("status","OK");
            placeValidationStepDefinaitions.verify_place_id_created_maps_to_using("Vijay","GetPlaceAPI");
        }

    }
}
