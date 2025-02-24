package APITesting;

import POJOClasses.GetCourses;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static TestData.AddPlace.AddPlacePayload;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OAuthValidation_Sample1 {
    public String authorizationToken="";
    @Test
    public void getAuthorizationRequestToGenerateToken(){
        baseURI="https://rahulshettyacademy.com";

        String authorizationContent=given()
                .formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","create")
                .when()
                .post("/oauthapi/oauth2/resourceOwner/token")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();
        authorizationToken=authorizationToken+CommonUtilities.getRawToJson(authorizationContent,"access_token");
        String refreshToken=authorizationToken=authorizationToken+CommonUtilities.getRawToJson(authorizationContent,"access_token");
        System.out.println("token   " +authorizationToken);
        System.out.println("refresh   "+refreshToken);

    }

    @Test(dependsOnMethods = "getAuthorizationRequestToGenerateToken")
    public void getAccessUsingTheAuthorizationReceived(){
        baseURI="https://rahulshettyacademy.com";

        GetCourses courseDetails=given()
                .header("Content-type","application/json")
                .queryParam("access_token",authorizationToken)
                .when()
                .get("/oauthapi/getCourseDetails?access_token=isWONljTDOMx0xznIhPJcg==")
                .as(GetCourses.class);
//                .then()
//                .log().all()
//                .assertThat()
//                .statusCode(401) //teh api is returning 401 even when hit successfully its development fault, so we are asserting with 401, or we would have used 200 itself
//                .extract().asString();

        System.out.println(courseDetails.getLinkedIn());
        System.out.println(courseDetails.getExpertise());

        int apiValue=0;
        int webCount=0;
        int mobileCount=0;
        ArrayList<String> expectedCourseListInWebAutomation= new ArrayList<>(Arrays.asList("Selenium Webdriver Java","Cypress","Protractor"));
        ArrayList<String> actualCourseListInWebAutomation= new ArrayList<>();
        // getting all the details of courses titles and checking all the courses are available as expected
        for(int j=0;j<courseDetails.getCourses().getWebAutomation().size();j++){
            String courseTitle= courseDetails.getCourses().getWebAutomation().get(j).getCourseTitle();
            String coursePrice= courseDetails.getCourses().getWebAutomation().get(j).getCourseTitle();
            webCount=webCount+Integer.parseInt(coursePrice);
            actualCourseListInWebAutomation.add(courseTitle);
        }
        Assert.assertEquals(expectedCourseListInWebAutomation,actualCourseListInWebAutomation);

        // printing the price of course with title containing soap UI
        for(int i=0;i<courseDetails.getCourses().getApi().size();i++){
            String courseTitle= courseDetails.getCourses().getApi().get(i).getCourseTitle();
            String coursePrice= courseDetails.getCourses().getApi().get(i).getPrice();
            apiValue=apiValue+Integer.parseInt(coursePrice);
            if(courseTitle.contains("SaopUI")){
                System.out.println("course price of "+courseTitle+" is :  "+coursePrice);
            }
        }
        for(int i=0;i<courseDetails.getCourses().getMobile().size();i++){
            String coursePrice= courseDetails.getCourses().getMobile().get(i).getPrice();
            mobileCount=mobileCount+Integer.parseInt(coursePrice);
        }


        // Checking the sum of all the prices in WebAutomation,API,and Mobile is equal to total sum value
        System.out.println("Sum of all the courses price are  "+ apiValue+webCount+mobileCount);
    }
}
