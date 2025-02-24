package APITesting;

import POJOClasses.AddPlaces;
import POJOClasses.Location;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SpecBuilderTest {

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

        RequestSpecification requestSpecBuilder= new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                setContentType("application/json")
                .addQueryParam("key","qaclick123").build();

        RequestSpecification request= given().
                spec(requestSpecBuilder)
                .body(places);

        ResponseSpecification responseSpecification= new ResponseSpecBuilder()
                .expectContentType("application/json")
                .expectStatusCode(200).build();

        Response response= request.when()
                .post("/maps/api/place/add/json")
                .then().spec(responseSpecification)
                .body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response();
        String responseString= response.asString();
        System.out.println("Complete altered response  \n"+ responseString);
        String place_id_received= CommonUtilities.getRawToJson(responseString,"place_id");


    }
}
