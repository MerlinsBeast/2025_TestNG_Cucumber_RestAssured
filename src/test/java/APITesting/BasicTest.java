package APITesting;

import TestData.AddPlace;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static TestData.AddPlace.AddPlacePayload;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class BasicTest {
    public static void main(String[] args) {


        RestAssured.baseURI="https://rahulshettyacademy.com";
       String response= given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(AddPlacePayload())
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().asString();

        String place_id_received=CommonUtilities.getRawToJson(response,"place_id");
        System.out.println("Place id value is "+place_id_received);


        // fetchig the details
        String getResponse=given()
                .queryParam("key","qaclick123")
                .queryParam("place_id",place_id_received)
                .when()
                .get("/maps/api/place/get/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();

        String initial_address_received=CommonUtilities.getRawToJson(getResponse,"address");
        System.out.println("Initial Address value is "+initial_address_received);

        // calling teh update api to update the address details for teh same place id
        String newAddress="70 Summer walk, USA";
        String updateResponse=given()
                .log().all()
                .queryParam("key","qaclick123")
                .header("Content-type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+place_id_received+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when()
                .put("maps/api/place/update/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .asString();

        String messageReceived=CommonUtilities.getRawToJson(updateResponse,"msg");
        Assert.assertEquals(messageReceived,"Address successfully updated");

        //calling the same get place again and checking the details for address are modified since we used update api earlier
        String getResponseAgain=given()
                .queryParam("key","qaclick123")
                .queryParam("place_id",place_id_received)
                .when()
                .get("/maps/api/place/get/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();

        String updated_address_received=CommonUtilities.getRawToJson(getResponseAgain,"address");
        System.out.println("Updated Address value is "+updated_address_received);
        Assert.assertFalse(initial_address_received.equals(updated_address_received));
        Assert.assertTrue(updated_address_received.equals(newAddress));


    }
}
