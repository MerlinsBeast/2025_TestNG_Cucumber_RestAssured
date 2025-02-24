package APITesting;

import POJOClasses.AddPlaces;
import POJOClasses.Location;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static TestData.AddPlace.AddPlacePayload;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SerilizationExample_AddPlace {

    @Test
    public void addPlace(){
        AddPlaces places= new AddPlaces();
        places.setAccuracy("50");
        places.setAddress("Prasan Niwas Kamlakar Nagar");
        places.setLanguage("Hindi Bhai");
        places.setPhone_number("8689946239");
        places.setName("Vijay Yadav");
        Location loc= new Location();
        loc.setLat("13.13");
        loc.setLng("14.14");
        places.setLocation(loc);
        places.setWebsites("vijay@gmail.com");
        places.setTypes(Arrays.asList("Vijay","Shrikrishna","Yadav"));
        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response response= given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(places)
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response();
        String responseString= response.asString();
        System.out.println("Complete altered response  \n"+ responseString);
        String place_id_received= CommonUtilities.getRawToJson(responseString,"place_id");


    }
}
