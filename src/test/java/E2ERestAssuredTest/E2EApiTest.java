package E2ERestAssuredTest;

import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class E2EApiTest {

    String token="";
    String userId="";
    String productId="";
    String orderId="";
    @Test
    public void login(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String loginResponse=given().log().all()
                .header("Content-type","application/json")
                .body("{\"userEmail\":\"yd276vijay@gmail.com\",\"userPassword\":\"Merlin@01Magic\"}")
                .when().log().all()
                .post("/api/ecom/auth/login")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().asString();
        token=token+CommonUtilities.getRawToJson(loginResponse,"token");
        userId=userId+CommonUtilities.getRawToJson(loginResponse,"userId");
    }

    @Test(dependsOnMethods = "login")
    public void createProduct(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String productCreated=given().log().all()
                .header("Authorization",token)
                .formParams("productName","qwerty")
                .formParams("productAddedBy",userId)
                .formParams("productCategory","fashions")
                .formParams("productSubCategory","tshirts")
                .formParams("productPrice","52232")
                .formParams("productDescription","jeans")
                .formParams("productFor","mens")
                .multiPart("productImage",new File("C://Users//Vijay_Yadav//Pictures//sign (4).png"))
                .when().log().all()
                .post("/api/ecom/product/add-product")
                .then().log().all()
                .extract().asString();

        productId=productId+CommonUtilities.getRawToJson(productCreated,"productId");
    }

    @Test(dependsOnMethods = "createProduct")
    public void createOrder(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String productCreated=given().log().all()
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"orders\":[{\"country\":\"India\",\"productOrderedId\":\""+productId+"\"}]}")
                .when().log().all()
                .post("/api/ecom/order/create-order")
                .then().log().all()
                .extract().asString();

        orderId=orderId+CommonUtilities.getRawToJson(productCreated,"orders[0]");
    }

    @Test(dependsOnMethods = "createOrder")
    public void viewOrder() throws InterruptedException {
        Thread.sleep(3000);
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String productCreated=given().log().all()
                .header("Authorization",token)
                .queryParam("id",orderId)
                .when().log().all()
                .get("/api/ecom/order/get-orders-details")
                .then().log().all()
                .extract().asString();

        String message=CommonUtilities.getRawToJson(productCreated,"message");
        System.out.println("message is :   "+message);
    }

    @Test(dependsOnMethods = "viewOrder")
    public void deleteOrder(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String productCreated=given().log().all()
                .header("Authorization",token)
                .pathParams("productId",productId)
                .when().log().all()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all()
                .extract().asString();

        String message=CommonUtilities.getRawToJson(productCreated,"message");
        System.out.println("message is :   "+message);
    }



}
